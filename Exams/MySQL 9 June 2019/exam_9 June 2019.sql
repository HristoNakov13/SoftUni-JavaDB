#------1------

CREATE TABLE clients(
	id INT AUTO_INCREMENT,
    full_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bank_accounts(
	id INT AUTO_INCREMENT,
    account_number VARCHAR(10) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    client_id INT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_bank_accounts_clients
		FOREIGN KEY(client_id)
        REFERENCES clients(id)
);

CREATE TABLE cards(
	id INT AUTO_INCREMENT,
    card_number VARCHAR(19) NOT NULL,
    card_status VARCHAR(7) NOT NULL,
    bank_account_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_cards_bank_accounts
		FOREIGN KEY(bank_account_id)
        REFERENCES bank_accounts(id)
);

CREATE TABLE branches(
	id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE employees(
	id INT AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    started_on DATE NOT NULL,
    branch_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_employees_branches
		FOREIGN KEY(branch_id)
        REFERENCES branches(id)
);

CREATE TABLE employees_clients(
	employee_id INT,
    client_id INT,
    CONSTRAINT fk_employees_clients_employees
		FOREIGN KEY(employee_id)
        REFERENCES employees(id),
	CONSTRAINT fk_employees_clients_clients
		FOREIGN KEY(client_id)
        REFERENCES clients(id)
);

#------2------

INSERT INTO cards (card_number, card_status, bank_account_id)
SELECT REVERSE(full_name), 'Active', id
FROM clients
WHERE id BETWEEN 191 AND 200;

#------3------

SELECT * 
FROM employees_clients
WHERE client_id = employee_id;

CREATE VIEW v_get_employee_id_with_lowest_clients_count AS
SELECT employee_id
FROM employees_clients
GROUP BY employee_id
ORDER BY COUNT(employee_id), employee_id
LIMIT 1;

UPDATE employees_clients
SET employee_id = (SELECT * FROM v_get_employee_id_with_lowest_clients_count)
WHERE client_id = employee_id;

#------4------
DELIMITER //

CREATE FUNCTION ufk_get_employees_clients_count(searched_employee_id INT)
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE clients_count INT;
    SET clients_count := (
		SELECT COUNT(client_id)
		FROM employees_clients
        WHERE client_id IS NOT NULL
		GROUP BY employee_id
		HAVING employee_id = 102
        );
        
	IF cards_count IS NULL
    THEN SET cards_count := 0;
    END IF;
    
	RETURN clients_count;
END //

DELIMITER ;

DELETE FROM employees
WHERE ufk_get_employees_clients_count(id) = 0;

#------5------

SELECT id, full_name
FROM clients
ORDER BY id;

#------6------

SELECT id, 
	CONCAT_WS(' ', first_name, last_name) full_name,
    CONCAT('$', salary) salary,
    started_on
FROM employees
WHERE salary >= 100000 AND started_on >= '2018-01-01'
ORDER BY salary DESC, id;

#------7------

SELECT cc.id,
	CONCAT_WS(' : ' , cc.card_number, c.full_name) card_token
FROM clients c
INNER JOIN bank_accounts ba
ON c.id = ba.client_id
INNER JOIN cards cc
ON cc.bank_account_id = ba.id
ORDER BY cc.id DESC;

#------8------

SELECT CONCAT_WS(' ', e.first_name, e.last_name) name,
	started_on,
    COUNT(ec.client_id) count_of_clients
FROM employees e
LEFT JOIN employees_clients ec
ON e.id = ec.employee_id
GROUP BY ec.employee_id
ORDER BY count_of_clients DESC, ec.employee_id
LIMIT 5;

#------9------

SELECT br.name, COUNT(cc.id) count_of_cards
FROM branches br
LEFT JOIN employees e
ON e.branch_id = br.id
LEFT JOIN employees_clients ec
ON e.id = ec.employee_id
LEFT JOIN clients c
ON c.id = ec.client_id
LEFT JOIN bank_accounts ba
ON c.id = ba.client_id
LEFT JOIN cards cc
ON cc.bank_account_id = ba.id
GROUP BY br.name
ORDER BY count_of_cards DESC, br.name;

#------10------

DELIMITER //

CREATE FUNCTION udf_client_cards_count(client_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE cards_count INT;
    
    SET cards_count := (
		SELECT COUNT(*)
        FROM clients c
        INNER JOIN bank_accounts ba
        ON c.id = ba.client_id
        INNER JOIN cards cc
        ON ba.id = cc.bank_account_id
        WHERE c.full_name = client_name
        GROUP BY c.id);
        
	IF cards_count IS NULL
    THEN SET cards_count := 0;
    END IF;
    
    RETURN cards_count;
END //

DELIMITER ;

SELECT full_name, 
	udf_client_cards_count(full_name) cards
FROM clients

#------11------

DELIMITER //
CREATE PROCEDURE udp_clientinfo(client_name VARCHAR(30))
BEGIN
	SELECT c.full_name,
		c.age,
        ba.account_number,
        CONCAT('$', ba.balance) balance
	FROM clients c
    INNER JOIN bank_accounts ba
    ON ba.client_id = c.id
    WHERE c.full_name = client_name;
END //

DELIMITER ;

CALL udp_clientinfo('Hunter Wesgate');