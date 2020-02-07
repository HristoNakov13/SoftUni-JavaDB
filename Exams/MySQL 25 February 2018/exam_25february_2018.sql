CREATE DATABASE buhtig;

USE buhtig;

#------1------

CREATE TABLE users(
	id INT AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE repositories(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE repositories_contributors(
	repository_id INT,
    contributor_id INT,
    CONSTRAINT fk_repositories_contributors_users 
		FOREIGN KEY(contributor_id)
		REFERENCES users(id),
	CONSTRAINT fk_repositories_contributors_repositories
		FOREIGN KEY(repository_id)
        REFERENCES repositories(id)
);

CREATE TABLE issues(
	id INT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    issue_status VARCHAR(6) NOT NULL,
    repository_id INT NOT NULL,
    assignee_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_issues_repositories
		FOREIGN KEY(repository_id)
        REFERENCES repositories(id),
	CONSTRAINT fk_issues_users
		FOREIGN KEY(assignee_id)
        REFERENCES users(id)
);

CREATE TABLE commits(
	id INT AUTO_INCREMENT,
    message VARCHAR(255) NOT NULL,
    issue_id INT,
    repository_id INT NOT NULL,
    contributor_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_commits_issues
		FOREIGN KEY(issue_id)
        REFERENCES issues(id),
	CONSTRAINT fk_commits_repositories
		FOREIGN KEY(repository_id)
        REFERENCES repositories(id),
	CONSTRAINT fk_commits_users
		FOREIGN KEY(contributor_id)
        REFERENCES users(id)
);

CREATE TABLE files(
	id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    size DECIMAL(10, 2) NOT NULL,
    parent_id INT,
    commit_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_files_files
		FOREIGN KEY(parent_id)
        REFERENCES files(id),
	CONSTRAINT fk_files_commits
		FOREIGN KEY(commit_id)
        REFERENCES commits(id)
);

#------2------

INSERT INTO issues(title, issue_status, repository_id, assignee_id)
SELECT CONCAT('Critical Problem With ', f.name, '!') title,
	'open' issue_status,
    CEIL((f.id * 2) / 3) repository_id,
    c.contributor_id assignee_id
FROM files f
INNER JOIN commits c
ON f.commit_id = c.id
WHERE f.id BETWEEN 46 AND 50;

#------3------

UPDATE repositories_contributors rc
JOIN
	(SELECT r.id repo_id
		FROM repositories r
		WHERE r.id NOT IN 
			(SELECT repository_id
            FROM repositories_contributors)
    ORDER BY r.id
    LIMIT 1) d 
SET rc.repository_id = d.repo_id
WHERE rc.contributor_id = rc.repository_id;

#------4------

DELETE FROM repositories r
WHERE r.id NOT IN(SELECT repository_id FROM issues);

#------5------

SELECT id, username
FROM users
ORDER BY id;

#------6------

SELECT repository_id, contributor_id
FROM repositories_contributors
WHERE repository_id = contributor_id
ORDER BY repository_id;

#------7------

SELECT id,
	name,
    size
FROM files
WHERE name LIKE '%html%' AND size > 1000
ORDER BY size DESC;

#------8------

SELECT i.id, 
	CONCAT_WS(' : ', u.username, i.title) issue_assignee
FROM issues i
INNER JOIN users u
ON i.assignee_id = u.id
ORDER BY i.id DESC;

#------9------

SELECT f.id,
	f.name,
    CONCAT(f.size, 'KB') size
FROM files f
LEFT JOIN files s
ON f.id = s.parent_id
WHERE s.id IS NULL
ORDER BY f.id;

#------10------

SELECT r.id,
	r.name,
    COUNT(i.id) issues
FROM issues i
INNER JOIN repositories r
ON i.repository_id = r.id
GROUP BY i.repository_id
ORDER BY issues DESC, r.id
LIMIT 5;

#------11------

SELECT r.id,
	r.name,
    (SELECT COUNT(*) FROM commits i WHERE i.id = r.id) commits,
    COUNT(rc.contributor_id) contributors
FROM repositories r
INNER JOIN repositories_contributors rc
ON r.id = rc.repository_id
GROUP BY rc.repository_id
ORDER BY contributors DESC, r.id
LIMIT 1;

#------12------

SELECT u.id,
    u.username,
    SUM(IF(c.contributor_id = u.id, 1, 0)) commits
FROM users u
LEFT JOIN issues i 
ON u.id = i.assignee_id
LEFT JOIN commits c 
ON i.id = c.issue_id
GROUP BY u.id
ORDER BY `commits` DESC , u.id;

#------13------

SELECT SUBSTRING(f.name, 1, CHAR_LENGTH(f.name) - LOCATE('.', REVERSE(f.name))) file,
	(
		SELECT COUNT(*) 
		FROM commits c 
		WHERE c.message 
		LIKE CONCAT('%', f.name, '%')
    ) recursive_count
FROM files f
INNER JOIN files j
ON f.id = j.parent_id AND f.parent_id = j.id AND f.id != f.parent_id;


#------14------

SELECT r.id, 
	r.name,
    COUNT(DISTINCT c.contributor_id) users
FROM repositories r
LEFT JOIN commits c
ON c.repository_id = r.id
GROUP BY r.id
ORDER BY users DESC, r.id;

#------15------

DELIMITER //

CREATE PROCEDURE udp_commit(username VARCHAR(30), password VARCHAR(30), message VARCHAR(255), issue_id INT)
BEGIN
	IF ((SELECT u.username FROM users u WHERE u.username = username) IS NULL)
    THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'No such user!';
    ELSEIF ((SELECT u.username FROM users u WHERE u.username = username AND u.password = password) IS NULL)
    THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Password is incorrect!';
    ELSEIF ((SELECT i.title FROM issues i WHERE i.id = issue_id) IS NULL)
    THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'The issue does not exist!';
	END IF;
    
    INSERT INTO commits(message, issue_id, repository_id, contributor_id)
    VALUES(
		message,
        issue_id,
        (SELECT i.repository_id FROM issues i WHERE i.id = issue_id),
        (SELECT u.id FROM users u WHERE u.username = username AND u.password = password)
        );
        
	UPDATE issues i
    SET i.issue_status = 'closed'
    WHERE i.id = issue_id;
END //


CALL udp_commit('WhoDenoteBel', 'ajmISQi', 'Fixed issue: Invalid welcoming message in READ.html', 2);

#------16------

DELIMITER //

CREATE PROCEDURE udp_findbyextension(extension VARCHAR(20))
BEGIN
	SELECT f.id,
		f.name,
        CONCAT(f.size, 'KB') size
	FROM files f
    WHERE f.name LIKE CONCAT('%', extension)
    ORDER BY f.id;
END //

CALL udp_findbyextension('html');