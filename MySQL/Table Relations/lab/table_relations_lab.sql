#------1------
USE `camp`;
    
CREATE TABLE `mountains`(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20)
    );
    
CREATE TABLE `peaks` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20),
    `mountain_id` INT NOT NULL,
    CONSTRAINT fk_peaks_mountains FOREIGN KEY (`mountain_id`) REFERENCES `mountains`(`id`)
    );
    
#------2------

SELECT v.driver_id, v.vehicle_type, CONCAT_WS(' ', c.first_name, c.last_name) 
FROM campers AS c
JOIN vehicles AS v
ON c.id = v.driver_id;

#------3------

SELECT r.starting_point AS route_starting_point,
	r.end_point AS route_ending_point,
    r.leader_id,
    CONCAT_WS(' ', c.first_name, c.last_name) AS leader_name
FROM campers AS c
JOIN routes AS r
ON c.id = r.leader_id;

#------4------

CREATE TABLE `mountains`(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20));
    
CREATE TABLE `peaks` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20),
    `mountain_id` INT NOT NULL,
    CONSTRAINT fk_peaks_mountains FOREIGN KEY (`mountain_id`) REFERENCES `mountains`(`id`)
    ON DELETE CASCADE);
    
#------5------

CREATE DATABASE `projects_managment`;

CREATE TABLE `employees`(
	`id` INT PRIMARY KEY,
    `first_name` VARCHAR(30),
    `last_name` VARCHAR(30)
    );
    
CREATE TABLE `projects`(
	`id` INT PRIMARY KEY,
    `project_lead_id` INT,
    CONSTRAINT fk_projects_employees FOREIGN KEY(`project_lead_id`) REFERENCES `employees`(`id`)
    );
    
CREATE TABLE `clients`(
	`id` INT PRIMARY KEY,
    `client_name` VARCHAR(30),
    `project_id` INT,
    CONSTRAINT fk_clients_projects FOREIGN KEY(`project_id`) REFERENCES `projects`(`id`)
    );
    
ALTER TABLE `employees`
ADD COLUMN `project_id` INT,
ADD CONSTRAINT fk_employees_projects FOREIGN KEY(`project_id`) REFERENCES `projects`(`id`);

ALTER TABLE `projects`
ADD COLUMN `client_id` INT AFTER `id`,
ADD CONSTRAINT fk_projects_clients FOREIGN KEY(`client_id`) REFERENCES `clients`(`id`);