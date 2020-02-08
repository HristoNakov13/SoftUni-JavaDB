CREATE DATABASE colonial_blog_db;

USE colonial_blog_db;

#------1------

CREATE TABLE users(
	id INT AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE categories(
	id INT AUTO_INCREMENT,
    category VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE articles(
	id INT AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    category_id INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_articles_categories
		FOREIGN KEY(category_id)
        REFERENCES articles(id)
);

CREATE TABLE users_articles(
	user_id INT,
    article_id INT,
    CONSTRAINT fk_users_articles_users
		FOREIGN KEY(user_id)
        REFERENCES users(id),
	CONSTRAINT fk_users_articles_articles
		FOREIGN KEY(article_id)
        REFERENCES articles(id)
);

CREATE TABLE comments(
	id INT AUTO_INCREMENT,
    comment VARCHAR(255) NOT NULL,
    article_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_comments_articles
		FOREIGN KEY(article_id)
        REFERENCES articles(id),
	CONSTRAINT fk_comments_users
		FOREIGN KEY(user_id)
        REFERENCES users(id)
);

CREATE TABLE likes(
	id INT AUTO_INCREMENT,
    article_id INT,
    comment_id INT,
    user_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_likes_articles
		FOREIGN KEY(article_id)
        REFERENCES articles(id),
	CONSTRAINT fk_likes_comments
		FOREIGN KEY(comment_id)
        REFERENCES comments(id),
	CONSTRAINT fk_likes_users
		FOREIGN KEY(user_id)
        REFERENCES users(id)
);

#------2------

INSERT INTO likes(article_id, comment_id, user_id)
SELECT 
	CASE
		WHEN u.id % 2 = 0 
		THEN CHAR_LENGTH(u.username)
		ELSE NULL
    END article_id,
    CASE
		WHEN u.id % 2 != 0 
		THEN CHAR_LENGTH(u.email)
		ELSE NULL
    END comment_id,
    u.id
FROM users u
WHERE u.id BETWEEN 16 AND 20;

#------3------

UPDATE comments
SET comment = 
	(CASE
		WHEN id % 2 = 0
        THEN 'Very good article.'
        WHEN id % 3 = 0
        THEN 'This is interesting.'
        WHEN id % 5 = 0
        THEN 'I definitely will read the article again.'
        WHEN id % 7 = 0
        THEN 'The universe is such an amazing thing.'
        ELSE comment
	END)	
WHERE id BETWEEN 1 AND 15;

#------4------

DELETE FROM articles 
WHERE category_id IS NULL;

#------5------

SELECT i.title, i.summary 
FROM 
	(SELECT id, title, CONCAT(SUBSTRING(content, 1, 20), '...') summary
	FROM articles
	ORDER BY CHAR_LENGTH(content) DESC, id
	LIMIT 3) i
ORDER BY i.id;

#------6------

SELECT a.id, 
	a.title
FROM articles a
INNER JOIN users_articles ua
ON a.id = ua.article_id
WHERE ua.article_id = ua.user_id
ORDER BY a.id;

#------7------

SELECT c.category,
	COUNT(a.id) articles,
	(SELECT COUNT(l.article_id)
	FROM articles a
	INNER JOIN likes l
	ON a.id = l.article_id
	WHERE a.category_id = c.id) likes
FROM categories c
INNER JOIN articles a
ON c.id = a.category_id
GROUP BY c.id
ORDER BY likes DESC, articles DESC, c.id;

#------8------

SELECT a.title,
	COUNT(c.id) comments
FROM articles a
INNER JOIN categories cr
ON a.category_id = cr.id
INNER JOIN comments c
ON a.id = c.article_id
WHERE cr.category = 'Social'
GROUP BY a.id
ORDER BY comments DESC
LIMIT 1;

#------9------

SELECT CONCAT(SUBSTRING(c.comment, 1, 20), '...') summary
FROM comments c
LEFT JOIN likes l
ON c.id = l.comment_id
WHERE l.comment_id IS NULL
ORDER BY c.id DESC;

#------10------

DELIMITER //

CREATE FUNCTION udf_users_articles_count(username VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE articles_count INT;
    SET articles_count := 
		(SELECT COUNT(ua.article_id) 
        FROM users_articles ua
        WHERE ua.user_id = (SELECT u.id FROM users u WHERE u.username = username));
	RETURN articles_count;
END//

SELECT u.username, udf_users_articles_count('UnderSinduxrein') AS count
FROM articles AS a
JOIN users_articles ua
ON a.id = ua.article_id
JOIN users u
ON ua.user_id = u.id
WHERE u.username = 'UnderSinduxrein'
GROUP BY u.id;

#------11------

DELIMITER //
CREATE PROCEDURE udp_like_article(username VARCHAR(30), title VARCHAR(30))
BEGIN
IF ((SELECT u.id FROM users u WHERE u.username = username) IS NULL)
    THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Non-existent user.';
ELSEIF ((SELECT a.id FROM articles a WHERE a.title = title) IS NULL)
    THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Non-existent article.';
END IF;
	
INSERT INTO LIKES(article_id, user_id)
VALUES((SELECT a.id 
		FROM articles a
		WHERE a.title = title),
            
		(SELECT u.id 
		FROM users u 
		WHERE u.username = username));
END//

DROP PROCEDURE udp_like_article;

CALL udp_like_article('Pesho123', 'Donnybrook, Victoria');

CALL udp_like_article('BlaAntigadsa', 'Na Pesho statiqta');

CALL udp_like_article('BlaAntigadsa', 'Donnybrook, Victoria');
SELECT a.title, u.username 
FROM articles a 
JOIN likes l
ON a.id = l.article_id
JOIN users u
ON l.user_id = u.id
WHERE u.username = 'BlaAntigadsa' AND a.title = 'Donnybrook, Victoria';