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

SELECT tc.card_number, tc.job_during_journey
FROM travel_cards tc
ORDER BY  tc.card_number;

#------6------

SELECT c.id, concat_ws(' ', c.first_name, c.last_name) full_name, c.ucn
FROM colonists c
ORDER BY c.first_name, c.last_name, c.id;

#------7------

SELECT j.id, j.journey_start, j.journey_end
FROM journeys j
WHERE j.purpose = 'Military'
ORDER BY j.journey_start;

#------8------

SELECT c.id, concat_ws(' ', c.first_name, c.last_name) full_name
FROM colonists c
JOIN travel_cards tc
ON c.id = tc.colonist_id
WHERE tc.job_during_journey = 'Pilot'
ORDER BY id;

#------9------

SELECT count(c.id) count
FROM colonists c
JOIN travel_cards tc
ON c.id = tc.colonist_id
JOIN journeys j
ON tc.journey_id = j.id
WHERE j.purpose = 'Technical';

#------10------

SELECT ship.name spaceship_name, port.name spaceport_name
FROM spaceships ship
JOIN journeys j on ship.id = j.spaceship_id
JOIN spaceports port ON j.destination_spaceport_id = port.id
ORDER BY ship.light_speed_rate DESC LIMIT 1;

#------11------

SELECT s.name, s.manufacturer
FROM colonists c
JOIN travel_cards tc
ON tc.colonist_id = c.id
JOIN journeys j
on tc.journey_id = j.id
JOIN spaceships s
on j.spaceship_id = s.id
WHERE year(c.birth_date) > year(DATE_SUB('2019-01-01', INTERVAL 30 YEAR)) and tc.job_during_journey = 'Pilot'
ORDER BY s.name;