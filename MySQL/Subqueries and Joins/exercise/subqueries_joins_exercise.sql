#------1------

USE `soft_uni`;

SELECT e.employee_id,
	e.job_title,
    e.address_id,
    a.address_text
FROM employees e
INNER JOIN addresses a
ON e.address_id = a.address_id
ORDER BY a.address_id
LIMIT 5;

#------2------


SELECT e.first_name,
	e.last_name,
    t.name,
    a.address_text
FROM employees e
INNER JOIN addresses a
ON e.address_id = a.address_id
JOIN towns t
ON a.town_id = t.town_id
ORDER BY e.first_name, e.last_name
LIMIT 5;

#------3------

SELECT e.employee_id,
	e.first_name,
	e.last_name,
    d.name
FROM employees e
INNER JOIN departments d
ON e.department_id = d.department_id
WHERE d.name = 'Sales'
ORDER BY e.employee_id DESC;

#------4------

SELECT e.employee_id,
	e.first_name,
	e.salary,
    d.name
FROM employees e
INNER JOIN departments d
ON e.department_id = d.department_id
WHERE e.salary > 15000
ORDER BY d.department_id DESC
LIMIT 5;

#------5------

SELECT e.employee_id, e.first_name
FROM employees e
LEFT JOIN employees_projects ep
ON e.employee_id = ep.employee_id
WHERE ep.project_id IS NULL
ORDER BY e.employee_id DESC
LIMIT 3;

#------6------

SELECT e.first_name,
	e.last_name,
    e.hire_date,
    d.name
FROM employees e
INNER JOIN departments d
ON e.department_id = d.department_id
WHERE DATE(e.hire_date) > '1999-1-1'
	AND d.name IN('Sales', 'Finance')
ORDER BY e.hire_date;

#------7------

SELECT e.employee_id,
	e.first_name,
    p.name
FROM employees e
INNER JOIN employees_projects ep
ON e.employee_id = ep.employee_id
INNER JOIN projects p
ON p.project_id = ep.project_id
WHERE DATE(p.start_date) > '2002-08-13'
	AND p.end_date IS NULL
ORDER BY e.first_name, p.name
LIMIT 5;

#------8------

SELECT e.employee_id, e.first_name,
    CASE
		WHEN YEAR(p.start_date) > 2004 THEN NULL
        ELSE p.name
    END AS project_name
FROM employees e
INNER JOIN employees_projects ep
ON e.employee_id = ep.employee_id
INNER JOIN projects p
ON p.project_id = ep.project_id
WHERE e.employee_id = 24
ORDER BY p.name;

#------9------

SELECT e.employee_id,
	e.first_name,
    m.employee_id,
    m.first_name AS manager_name
FROM employees e
INNER JOIN employees m
ON e.manager_id = m.employee_id
WHERE e.manager_id IN (3, 7)
ORDER BY e.first_name;

#------10------

SELECT e.employee_id,
	CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
    CONCAT(m.first_name, ' ', m.last_name) AS manager_name,
    d.name AS department_name
FROM employees e
INNER JOIN employees m
ON e.manager_id = m.employee_id
INNER JOIN departments d
ON e.department_id = d.department_id
WHERE e.manager_id IS NOT NULL
ORDER BY e.employee_id
LIMIT 5;

#------11------

SELECT AVG(salary) AS min_average_salary
FROM employees
GROUP BY department_id
ORDER BY min_average_salary
LIMIT 1;

#------12------

USE `geography`;

SELECT c.country_code,
	m.mountain_range,
    p.peak_name,
    p.elevation
FROM countries c
INNER JOIN mountains_countries mc
ON mc.country_code = c.country_code
INNER JOIN mountains m
ON m.id = mc.mountain_id
INNER JOIN peaks p
ON p.mountain_id = m.id
WHERE p.elevation > 2835 AND c.country_code = 'BG'
ORDER BY p.elevation DESC;

#------13------

SELECT c.country_code,
	COUNT(m.mountain_range) AS mountain_ranges
FROM countries c
INNER JOIN mountains_countries mc
ON c.country_code = mc.country_code
INNER JOIN mountains m
ON mc.mountain_id = m.id
WHERE c.country_code IN('BG', 'RU', 'US')
GROUP BY c.country_code
ORDER BY mountain_ranges DESC;

#------14------

SELECT c.country_name, r.river_name
FROM countries c
INNER JOIN continents ct
ON c.continent_code = ct.continent_code
LEFT JOIN countries_rivers cr
ON c.country_code = cr.country_code
LEFT JOIN rivers r
ON cr.river_id = r.id
WHERE ct.continent_code = 'AF'
ORDER BY c.country_name;

#------15------

#TODO

#------16------

SELECT COUNT(*)
FROM countries c
LEFT JOIN mountains_countries mc
ON c.country_code = mc.country_code
WHERE mc.mountain_id IS NULL;

#------17------

#TODO