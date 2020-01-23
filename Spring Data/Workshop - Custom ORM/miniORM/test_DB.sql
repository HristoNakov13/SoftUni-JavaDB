CREATE DATABASE internet_cafe;

USE internet_cafe;

CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    registration_date DATE NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users (username, age, registration_date) 
VALUES ('Ivan', 15, NOW()), 
	('Petar', 22, NOW());