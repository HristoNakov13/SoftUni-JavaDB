#------1------
USE `restaurant`;

SELECT `department_id`, COUNT(`id`)
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

#------2------

SELECT `department_id`, ROUND(AVG(`salary`), 2)
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

#------3------

SELECT `department_id`, ROUND(MIN(`salary`), 2) AS `min_salary`
FROM `employees`
GROUP BY `department_id`
HAVING `min_salary` > 800
ORDER BY `department_id`;

#------4------

SELECT COUNT(`id`)
FROM `products`
WHERE `price` > 8
GROUP BY `category_id`
HAVING `category_id` = 2;

# SELECT COUNT(`id`)
# FROM `products`
# WHERE `price` > 8 AND `category_id` = 2;

#------5------

SELECT `category_id`,
		ROUND(AVG(`price`), 2) AS `Average Price`,
        MIN(`price`) AS `Cheapest Product`,
        MAX(`price`) AS `Most Expensive Product`
FROM `products`
GROUP BY `category_id`
