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
