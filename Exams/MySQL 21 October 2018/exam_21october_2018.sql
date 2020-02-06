CREATE DATABASE colonial_journey_management;

USE colonial_journey_management;

#------1------

CREATE TABLE planets(
	id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE spaceports(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    planet_id INT,
    PRIMARY KEY(id),
	CONSTRAINT fk_spaceports_planets 
		FOREIGN KEY(planet_id) 
        REFERENCES planets(id)
);

CREATE TABLE spaceships(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(30) NOT NULL,
    light_speed_rate INT DEFAULT 0,
    PRIMARY KEY(id)
);

CREATE TABLE journeys(
	id INT AUTO_INCREMENT,
    journey_start DATETIME NOT NULL,
    journey_end DATETIME NOT NULL,
    purpose ENUM('Medical', 'Technical', 'Educational', 'Military'),
    destination_spaceport_id INT,
    spaceship_id INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_journeys_spaceports 
		FOREIGN KEY(destination_spaceport_id) 
        REFERENCES spaceports(id),
	CONSTRAINT fk_journeys_spaceships 
		FOREIGN KEY(spaceship_id) 
        REFERENCES spaceships(id)
);

CREATE TABLE colonists(
	id INT AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    ucn CHAR(10) UNIQUE NOT NULL,
    birth_date DATE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE travel_cards(
	id INT AUTO_INCREMENT,
    card_number CHAR(10) UNIQUE NOT NULL,
    job_during_journey ENUM('Pilot', 'Engineer', 'Trooper', 'Cleaner', 'Cook'),
    colonist_id INT,
    journey_id INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_travel_cards_colonists 
		FOREIGN KEY(colonist_id) 
        REFERENCES colonists(id),
	CONSTRAINT fk_travel_cards_journeys 
		FOREIGN KEY(journey_id) 
        REFERENCES journeys(id)
);

#------2------

INSERT INTO travel_cards (card_number, job_during_journey, colonist_id, journey_id)
SELECT (CASE
		WHEN c.birth_date > '1980-01-01' 
			THEN CONCAT(YEAR(c.birth_date), DAY(c.birth_date), SUBSTRING(c.ucn, 1, 4))
        ELSE CONCAT(YEAR(c.birth_date), MONTH(c.birth_date), SUBSTRING(c.ucn, 7, 10))
        END) card_number,
        (CASE
			WHEN c.id % 2 = 0 THEN 'Pilot'
            WHEN c.id % 3 = 0 THEN 'Cook'
            ELSE 'Engineer'
		END) job_during_journey,
        c.id,
        SUBSTRING(c.ucn, 1, 1) journey_id
FROM colonists c
WHERE c.id BETWEEN 96 AND 100;

#------3------

UPDATE journeys
SET purpose = 
	(CASE 
    WHEN id % 2 = 0 THEN 'Medical'
    WHEN id % 3 = 0 THEN 'Technical'
    WHEN id % 5 = 0 THEN 'Educational'
    WHEN id % 7 = 0 THEN 'Military'
    ELSE purpose
    END);


#------4------

DELETE FROM colonists c
WHERE (
		SELECT tc.id 
		FROM travel_cards tc 
        WHERE tc.colonist_id = c.id
        ) IS NULL;
#DELETE FROM colonists
#  WHERE id NOT IN (
#   SELECT tc.colonist_id FROM travel_cards tc
# );

#------5------

SELECT card_number, job_during_journey
FROM travel_cards
ORDER BY card_number;

#------6------

SELECT id, 
	CONCAT_WS(' ', first_name, last_name) full_name,
    ucn
FROM colonists
ORDER BY first_name, last_name, id;

#------7------

SELECT id,
	journey_start,
    journey_end
FROM journeys
WHERE purpose = 'Military'
ORDER BY journey_start;

#------8------

SELECT c.id,
	CONCAT_WS(' ', c.first_name, c.last_name) full_name
FROM colonists c
INNER JOIN travel_cards tc
ON c.id = tc.colonist_id
WHERE tc.job_during_journey = 'Pilot'
ORDER BY c.id;

#------9------

SELECT COUNT(c.id)
FROM colonists c
INNER JOIN travel_cards tc
ON c.id = tc.colonist_id
INNER JOIN journeys j
ON tc.journey_id = j.id
WHERE j.purpose = 'Technical';

#------10------

SELECT s.name, sp.name
FROM spaceships s
INNER JOIN journeys j
ON s.id = j.spaceship_id
INNER JOIN spaceports sp
ON j.destination_spaceport_id = sp.id
ORDER BY s.light_speed_rate DESC
LIMIT 1;

#------11------

SELECT s.name, s.manufacturer
FROM colonists c
INNER JOIN travel_cards tc
ON c.id = tc.colonist_id
INNER JOIN journeys j
ON tc.journey_id = j.id
INNER JOIN spaceships s
ON j.spaceship_id = s.id
WHERE tc.job_during_journey = 'Pilot' AND c.birth_date >= '1989-01-01'
ORDER BY s.name;

#------12------

SELECT p.name planet_name,
	s.name spaceport_name
FROM journeys j
INNER JOIN spaceports s
ON j.destination_spaceport_id = s.id
INNER JOIN planets p
ON s.planet_id = p.id
WHERE j.purpose = 'Educational'
ORDER BY s.name DESC;

#------13------

SELECT p.name, COUNT(j.id) journeys_count
FROM journeys j
INNER JOIN spaceports s
ON j.destination_spaceport_id = s.id
INNER JOIN planets p
ON s.planet_id = p.id
GROUP BY p.name
ORDER BY journeys_count DESC, p.name;

#------14------

SELECT j.id,
	p.name planet_name,
    s.name spaceport_name,
    j.purpose journey_purpose
FROM journeys j
INNER JOIN spaceports s
ON j.destination_spaceport_id = s.id
INNER JOIN planets p
ON s.planet_id = p.id
ORDER BY (j.journey_start - j.journey_end) DESC
LIMIT 1;

#------15------

SELECT tc.job_during_journey
FROM journeys j
INNER JOIN travel_cards tc
ON j.id = tc.journey_id
ORDER BY (j.journey_start - j.journey_end), 
	(SELECT COUNT(tci.job_during_journey) 
    FROM travel_cards tci 
    WHERE tci.journey_id = tc.journey_id) 
LIMIT 1;

#------16------

DELIMITER //

CREATE FUNCTION udf_count_colonists_by_destination_planet (planet_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE colonists_count INT;
    SET colonists_count := (SELECT COUNT(tc.colonist_id)
		FROM planets p
        INNER JOIN spaceports s
        ON p.id = s.planet_id
        INNER JOIN journeys j
        ON s.id = j.destination_spaceport_id
        INNER JOIN travel_cards tc
        ON j.id = tc.journey_id
        WHERE p.name = planet_name
        );
	RETURN colonists_count;
END //

#SELECT p.name, udf_count_colonists_by_destination_planet('Otroyphus') AS count
#FROM planets AS p
#WHERE p.name = 'Otroyphus';

#------17------

DELIMITER //

CREATE PROCEDURE udp_modify_spaceship_light_speed_rate(spaceship_name VARCHAR(50), 
	light_speed_rate_increse INT(11))
BEGIN 
	START TRANSACTION;
    
	UPDATE spaceships s
    SET s.light_speed_rate = light_speed_rate + light_speed_rate_increse
    WHERE s.name = spaceship_name;
    
    IF((SELECT COUNT(*) FROM spaceships sd WHERE sd.name = spaceship_name) = 1)
		THEN COMMIT;
        ELSE 
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Spaceship you are trying to modify does not exists.';
			ROLLBACK;
	END IF;
END //

DELIMITER ;

CALL udp_modify_spaceship_light_speed_rate ('USS d', 55);
SELECT name, light_speed_rate FROM spaceships WHERE name = 'USS Templar';