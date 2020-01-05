CREATE TABLE `towns` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name` VARCHAR(20) NOT NULL
);

CREATE TABLE `addresses` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`address_text` VARCHAR(20) NOT NULL,
`town_id` INT NOT NULL,
CONSTRAINT fk_addresses_towns FOREIGN KEY (`town_id`) REFERENCES `towns` (`id`)
);

CREATE TABLE `departments` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name` VARCHAR(20) NOT NULL
);

CREATE TABLE `employees` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`first_name` VARCHAR(20) NOT NULL,
`middle_name` VARCHAR(20) NULL,
`last_name` VARCHAR(20) NOT NULL,
`job_title` VARCHAR(30) NOT NULL,
`department_id` INT NOT NULL,
`hire_date` DATE NOT NULL,
`salary` DECIMAL NOT NULL,
`address_id` INT NOT NULL,
CONSTRAINT fk_employees_departments FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`),
CONSTRAINT fk_employees_addresses FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
);