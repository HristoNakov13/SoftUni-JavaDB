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

#------3------

CREATE PROCEDURE usp_get_towns_starting_with(starting_with VARCHAR(50))
BEGIN
	SELECT name
    FROM towns
    WHERE name LIKE (CONCAT(starting_with, '%'))
    ORDER BY name;
END //

#------4------

CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
BEGIN
	SELECT e.first_name, e.last_name
    FROM employees e
    INNER JOIN addresses a
    ON a.address_id = e.address_id
    INNER JOIN towns t
    ON t.town_id = a.town_id
    WHERE t.name = town_name
    ORDER BY e.first_name, e.last_name, e.employee_id;
END //

#------5------

CREATE FUNCTION ufn_get_salary_level(employee_salary DECIMAL(19, 4))
RETURNS VARCHAR(10)
BEGIN
    DECLARE salary_level VARCHAR(10);
    CASE
		WHEN employee_salary < 30000 THEN SET salary_level :='Low';
		WHEN employee_salary <= 50000 THEN SET salary_level := 'Average';
		ELSE SET salary_level := 'High';
	END CASE;
	RETURN salary_level;
END //

#------6------

CREATE PROCEDURE usp_get_employees_by_salary_level(salary_level VARCHAR(10))
BEGIN
	SELECT e.first_name, e.last_name
    FROM employees e
    WHERE
		CASE 
			WHEN salary_level = 'Low' THEN e.salary < 30000
			WHEN salary_level = 'Average' THEN e.salary <= 50000
			WHEN salary_level = 'High' THEN e.salary > 50000
		END
    ORDER BY e.first_name DESC, e.last_name DESC;
END //

#------7------

CREATE FUNCTION ufn_is_word_comprised(set_of_letters VARCHAR(50), word VARCHAR(50))
RETURNS INT
BEGIN
	RETURN word REGEXP CONCAT('^[', set_of_letters, ']+$');
END; //
        
#------8------

CREATE PROCEDURE usp_get_holders_full_name()
BEGIN
	SELECT CONCAT_WS(' ', first_name, last_name) full_name
    FROM account_holders
    ORDER BY full_name, id;
END //

#------9------

CREATE PROCEDURE usp_get_holders_with_balance_higher_than(balance_param DECIMAL(19, 4))
BEGIN
	SELECT h.first_name, h.last_name
    FROM account_holders h
    INNER JOIN accounts a
    ON a.account_holder_id = h.id
    GROUP BY a.account_holder_id
    HAVING SUM(a.balance) > balance_param
    ORDER BY a.id, h.first_name, h.last_name;
END //

#------10------

CREATE FUNCTION ufn_calculate_future_value(ammount DECIMAL(19, 4), interest_rate DOUBLE, number_of_years INT)
RETURNS DECIMAL(19, 4)
BEGIN
	DECLARE future_value DECIMAL(19, 4);
    SET future_value := ammount * (POWER((1 + interest_rate), number_of_years));
    RETURN future_value;
END //

#------11------

CREATE PROCEDURE usp_calculate_future_value_for_account(account_id INT, interest_rate DOUBLE)
BEGIN
	SELECT a.id,
		h.first_name, 
		h.last_name, 
		a.balance current_balance,
        ufn_calculate_future_value(a.balance, interest_rate, 5) balance_in_5_years
	FROM account_holders h
    INNER JOIN accounts a
    ON a.account_holder_id = h.id
    WHERE a.id = `account_id`;
END //

#------12------

CREATE PROCEDURE usp_deposit_money(account_id INT, money_ammount DECIMAL(19, 4))
BEGIN
	START TRANSACTION;
    IF(money_ammount <= 0) 
		THEN ROLLBACK;
	ELSE
		UPDATE accounts
        SET balance = balance + money_ammount
        WHERE id = account_id;
        COMMIT;
	END IF;
END //

#------13------

CREATE PROCEDURE usp_withdraw_money(account_id INT, money_ammount DECIMAL(19, 4))
BEGIN
	START TRANSACTION;
	IF(money_ammount <= 0) OR (SELECT balance FROM accounts WHERE id = account_id) - money_ammount < 0
		THEN ROLLBACK;
	ELSE
		UPDATE accounts
		SET balance = balance - money_ammount
		WHERE id = account_id;
		COMMIT;
	END IF;
END //

#------14------

CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, transfer_ammount DECIMAL(19, 4))
BEGIN
	START TRANSACTION;
    IF transfer_ammount <= 0
		THEN ROLLBACK;
	ELSEIF COUNT((SELECT balance FROM accounts WHERE id = from_account_id)) <> 1
		THEN ROLLBACK;
	ELSEIF COUNT((SELECT balance FROM accounts WHERE id = to_account_id)) <> 1
		THEN ROLLBACK;
	ELSE
		UPDATE accounts
        SET balance = balance - transfer_ammount
        WHERE id = from_account_id;
        
        UPDATE accounts
        SET balance = balance + transfer_ammount
        WHERE id = to_account_id;
        
        COMMIT;
    END IF;
END //

#------15------

DELIMITER ;

CREATE TABLE `logs`(
	log_id INT NOT NULL AUTO_INCREMENT,
    account_id INT NOT NULL,
    old_sum DECIMAL(19, 4),
    new_sum DECIMAL(19, 4),
    PRIMARY KEY (log_id)
#    CONSTRAINT fk_logs_account FOREIGN KEY (log_id) REFERENCES accounts(id)
);
DELIMITER //

CREATE TRIGGER tr_logs
AFTER UPDATE
ON accounts
FOR EACH ROW
BEGIN
	IF NOT (NEW.balance <=> OLD.balance)
		THEN 
			INSERT INTO logs(account_id, old_sum, new_sum) 
            VALUES(OLD.id, OLD.balance, NEW.balance);
	END IF;
END//

UPDATE accounts
SET balance = balance + 10
WHERE id = 1;

SELECT balance FROM accounts WHERE id = 1;

#------16------

DELIMITER ;

CREATE TABLE notification_emails(
	id INT NOT NULL AUTO_INCREMENT,
    recipient INT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
#    CONSTRAINT fk_notification_emails_accounts FOREIGN KEY(recipient)
#		REFERENCES accounts(id)
);

DELIMITER //
CREATE TRIGGER tr_notification_emails
AFTER INSERT
ON logs
FOR EACH ROW
BEGIN
	INSERT INTO notification_emails(recipient, subject, body)
		VALUES(NEW.account_id, 
			CONCAT('Balance change for account: ', NEW.account_id),
            CONCAT('On date ', DATE(NOW()), ' your balance was changed from ', NEW.old_sum, ' to ', NEW.new_sum, '.'));
END //