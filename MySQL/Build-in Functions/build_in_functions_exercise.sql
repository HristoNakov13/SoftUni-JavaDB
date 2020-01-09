#--------1--------

USE `soft_uni`;

SELECT `first_name`, `last_name` FROM `employees`
WHERE SUBSTRING(`first_name`, 1, 2) = 'Sa';

#--------2--------

SELECT `first_name`, `last_name` FROM `employees`
WHERE LOCATE('ei', `last_name`) > 0;

#--------3--------

SELECT `first_name` FROM `employees`
WHERE `department_id` IN(3, 10) AND EXTRACT(YEAR FROM `hire_date`) BETWEEN 1995 AND 2005
ORDER BY `employee_id`;

#--------4--------

SELECT `first_name`, `last_name` FROM `employees`
WHERE LOCATE('engineer', `job_title`) = 0
ORDER BY `employee_id`;

#--------5--------

SELECT `name` FROM `towns`
WHERE CHAR_LENGTH(`name`) IN(5, 6)
ORDER BY `name`;

#--------6--------

SELECT `town_id`, `name` FROM `towns`
WHERE SUBSTRING(`name`, 1, 1) IN('m', 'k', 'b', 'e')
ORDER BY `name`;

#--------7--------

SELECT `town_id`, `name` FROM `towns`
WHERE SUBSTRING(`name`, 1, 1) NOT IN('r', 'b', 'd')
ORDER BY `name`;

#--------8--------

CREATE VIEW v_employees_hired_after_2000 AS
SELECT `first_name`, `last_name` FROM `employees`
WHERE EXTRACT(YEAR FROM `hire_date`) > 2000;

# SELECT * FROM v_employees_hired_after_2000;

#--------9--------

SELECT `first_name`, `last_name` FROM `employees`
WHERE CHAR_LENGTH(`last_name`) = 5;

#--------10--------

USE `geography`;

SELECT `country_name`, `iso_code` FROM `countries`
WHERE CHAR_LENGTH(`country_name`) - CHAR_LENGTH(REPLACE(LOWER(`country_name`), 'a', '')) >= 3
ORDER BY `iso_code`;

#--------11--------

SELECT `peak_name`, `river_name`, LOWER(CONCAT(`peak_name`, SUBSTRING(`river_name`, 2))) AS `mix` 
FROM `peaks`, `rivers`
WHERE RIGHT(`peak_name`, 1) = LEFT(`river_name`, 1)
ORDER BY `mix`;

#--------12--------

USE `diablo`;

SELECT `name`, DATE_FORMAT(`start`, '%Y-%m-%d') FROM `games`
WHERE EXTRACT(YEAR FROM start) IN(2011, 2012)
ORDER BY `start`, `name`
LIMIT 50;

#--------13--------

SELECT `user_name`, SUBSTRING(`email`, LOCATE('@', `email`) + 1) AS `Email Provider`
FROM `users`
ORDER BY `Email Provider`, `user_name`; 

#--------14--------

SELECT `user_name`, `ip_address` FROM `users`
WHERE `ip_address` LIKE('___.1%.%.___')
ORDER BY `user_name`;

#--------15--------

#TODO

#--------16--------

USE `orders`;

SELECT `product_name`, 
`order_date`, 
DATE_ADD(`order_date`, INTERVAL 3 DAY) AS `pay_due`, 
DATE_ADD(`order_date`, INTERVAL 1 MONTH) AS `delivery_due` 
FROM `orders`;
