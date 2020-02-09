CREATE DATABASE lol;
 
USE lol;
 
#------1------
 
CREATE TABLE coaches(
    id INT AUTO_INCREMENT,
    first_name VARCHAR(10) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    salary DECIMAL(10, 2) default 0 NOT NULL,
    coach_level INT DEFAULT 0 NOT NULL,
    PRIMARY KEY(id)
);
 
CREATE TABLE countries(
    id INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id)
);
 
 
CREATE TABLE towns(
    id INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    country_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_towns_countries
        FOREIGN KEY(country_id)
        REFERENCES countries(id)
);
 
 
CREATE TABLE stadiums(
    id INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    capacity INT NOT NULL,
    town_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_stadiums_towns
        FOREIGN KEY(town_id)
        REFERENCES towns(id)
);
 
CREATE TABLE teams(
    id INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    established DATE NOT NULL,
    fan_base BIGINT(20) DEFAULT 0 NOT NULL,
    stadium_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_teams_stadiums
        FOREIGN KEY(stadium_id)
        REFERENCES stadiums(id)
);
 
CREATE TABLE skills_data(
    id INT AUTO_INCREMENT,
    dribbling INT DEFAULT 0,
    pace INT DEFAULT 0,
    passing INT DEFAULT 0,
    shooting INT DEFAULT 0,
    speed INT DEFAULT 0,
    strength INT DEFAULT 0,
    PRIMARY KEY(id)
);
 
CREATE TABLE players(
    id INT AUTO_INCREMENT,
    first_name VARCHAR(10) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    age INT DEFAULT 0 NOT NULL,
    position CHAR(1) NOT NULL,
    salary DECIMAL(10, 2) DEFAULT 0 NOT NULL,
    hire_date DATETIME,
    skills_data_id INT NOT NULL,
    team_id INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_players_skills_data
        FOREIGN KEY(skills_data_id)
        REFERENCES skills_data(id),
    CONSTRAINT fk_players_teams
        FOREIGN KEY(team_id)
        REFERENCES teams(id)
);
 
CREATE TABLE players_coaches(
    player_id INT,
    coach_id INT,
    PRIMARY KEY(player_id, coach_id),
    CONSTRAINT fk_players_coaches_players
        FOREIGN KEY(player_id)
        REFERENCES players(id),
    CONSTRAINT fk_players_coaches_coaches
        FOREIGN KEY(coach_id)
        REFERENCES coaches(id)
);
 
#------2------
 
INSERT INTO coaches(first_name, last_name, salary, coach_level)
SELECT p.first_name, p.last_name, p.salary * 2 salary, CHAR_LENGTH(p.first_name) coach_level
FROM players p
WHERE p.age >= 45;
 
#------3------
 
UPDATE coaches c
SET c.coach_level = c.coach_level + 1
WHERE c.first_name LIKE 'A%'AND
(SELECT COUNT(pc.player_id) FROM players_coaches pc WHERE pc.coach_id = c.id) >= 1;
 
SELECT * FROM coaches c
WHERE c.first_name LIKE 'A%'AND
(SELECT COUNT(pc.player_id) FROM players_coaches pc WHERE pc.coach_id = 2) >= 1;
 
#------4------
 
DELETE FROM players
WHERE age >= 45;
 
#------5------
 
SELECT first_name,
    age,
    salary
FROM players
ORDER BY salary DESC;
 
#------6------
 
SELECT p.id,
    CONCAT(p.first_name, ' ', p.last_name) full_name,
    p.age,
    p.position,
    p.hire_date
FROM players p
INNER JOIN skills_data sd
ON p.skills_data_id = sd.id
WHERE p.age < 23 AND  p.hire_date IS NULL AND p.position = 'A' AND sd.strength > 50
ORDER BY p.salary, p.age;
 
#------7------
 
SELECT t.name,
    t.established,
    t.fan_base,
    COUNT(p.id) players_count
FROM teams t
LEFT JOIN players p
ON t.id = p.team_id
GROUP BY t.id
ORDER BY players_count DESC, t.fan_base DESC;
 
#------8------
 
SELECT MAX(sd.speed) max_speed, tw.name
#p.*, sd.*
FROM teams t
LEFT JOIN players p
ON p.team_id = t.id
LEFT JOIN stadiums s
ON s.id = t.stadium_id
LEFT JOIN towns tw
ON s.town_id = tw.id
LEFT JOIN skills_data sd
ON p.skills_data_id = sd.id
WHERE t.name != 'Devify'
GROUP BY tw.id
ORDER BY max_speed DESC, tw.name;
 
#------9------
 
SELECT c.name,
    COUNT(p.id) total_count_of_players,
    SUM(p.salary) total_sum_of_salaries
FROM countries c
LEFT JOIN towns t
ON c.id = t.country_id
LEFT JOIN stadiums s
ON s.town_id = t.id
LEFT JOIN teams ta
ON ta.stadium_id = s.id
LEFT JOIN players p
ON ta.id = p.team_id
GROUP BY c.id
ORDER BY total_count_of_players DESC, c.name;
 
#------10------
 
DELIMITER //
 
CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE players_count INT;
    SET players_count :=
        (SELECT COUNT(p.id)
        FROM stadiums s
        INNER JOIN teams t
        ON s.id = t.stadium_id
        INNER JOIN players p
        ON p.team_id = t.id
        WHERE s.name = stadium_name);
       
        RETURN players_count;
END//
 
DROP FUNCTION udf_stadium_players_count
 
 
 
SELECT st.name, t.name, COUNT(p.id) FROM players p
INNER JOIN teams t
ON p.team_id = t.id
INNER JOIN stadiums st
ON t.stadium_id = st.id
GROUP BY st.id;
 
 
 
SELECT udf_stadium_players_count ('Dabjam') as `count`;
 
SELECT udf_stadium_players_count ('Vidoo') as `count`;
 
#------11-----
 
DELIMITER //
 
CREATE PROCEDURE udp_find_playmaker(min_dribble_points INT, team_name VARCHAR(45))
BEGIN
    SELECT CONCAT_WS(' ', p.first_name, p.last_name) full_name,
        p.age,
        p.salary,
        sd.dribbling,
        sd.speed,
        t.name
    FROM teams t
    INNER JOIN players p
    ON t.id = p.team_id
    INNER JOIN skills_data sd
    ON p.skills_data_id = sd.id
    WHERE t.name = team_name AND sd.dribbling > min_dribble_points
        AND sd.speed > (SELECT AVG(id.speed) FROM players ip INNER JOIN skills_data id WHERE ip.team_id = t.id)
    ORDER BY sd.speed DESC
    LIMIT 1;
 
END //
 
 
 
CALL udp_find_playmaker (2, 'Gabtype');
 
SELECT * FROM players p
INNER JOIN teams t
ON p.team_id = t.id
INNER JOIN skills_data sd
ON p.skills_data_id = sd.id
WHERE t.name = 'Gabtype';