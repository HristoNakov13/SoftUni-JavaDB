DELIMITER //

#------1------

USE soft_uni;

CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN
	SELECT first_name, last_name
	FROM employees
	WHERE salary > 35000
	ORDER BY first_name, last_name, employee_id;
END //

#------2------

CREATE PROCEDURE usp_get_employees_salary_above(salary_param DECIMAL(19, 4))
BEGIN
		SELECT first_name, last_name
	FROM employees
	WHERE salary >= salary_param
	ORDER BY first_name, last_name, employee_id;
END //


DELIMITER ;