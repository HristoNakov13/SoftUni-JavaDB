CREATE DATABASE instagraph;

USE instagraph;

#------1------

CREATE TABLE pictures(
	id INT AUTO_INCREMENT,
    path VARCHAR(255) NOT NULL,
    size DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE users(
	id INT AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL,
    profile_picture_id INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_users_pictures
		FOREIGN KEY(profile_picture_id)
        REFERENCES pictures(id)
);

CREATE TABLE users_followers(
	user_id INT,
    follower_id INT,
    CONSTRAINT fk_users_followers_users
		FOREIGN KEY(user_id)
        REFERENCES users(id),
	CONSTRAINT fk_users_followers_followers
		FOREIGN KEY(follower_id)
        REFERENCES users(id)
);

CREATE TABLE posts(
	id INT AUTO_INCREMENT,
    caption VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    picture_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_posts_users
		FOREIGN KEY(user_id)
        REFERENCES users(id),
	CONSTRAINT fk_posts_pictures
		FOREIGN KEY(picture_id)
        REFERENCES pictures(id)
);

CREATE TABLE comments(
	id INT AUTO_INCREMENT,
    content VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_comments_users
		FOREIGN KEY(user_id)
        REFERENCES users(id),
	CONSTRAINT fk_comments_posts
		FOREIGN KEY(post_id)
        REFERENCES posts(id)
);

#------2------

INSERT INTO comments(content, user_id, post_id)
SELECT CONCAT('Omg!', u.username, '!This is so cool!') content,
	CEIL(p.id * 3 / 2) user_id,
    p.id post_id
FROM posts p
INNER JOIN users u
ON u.id = p.user_id
WHERE p.id BETWEEN 1 AND 10;

#------3------

UPDATE users u
SET u.profile_picture_id = 
	IF(
		(SELECT COUNT(*) FROM users_followers uf WHERE uf.user_id = u.id) = 0, 
		u.id, 
		(SELECT COUNT(*) FROM users_followers uf WHERE uf.user_id = u.id)
	)
WHERE u.profile_picture_id IS NULL;

#------4------

DELETE FROM users u
WHERE (SELECT COUNT(*)
	FROM users_followers uf 
    WHERE uf.user_id = u.id
    ) = 0 
    AND (SELECT COUNT(*) 
    FROM users_followers uf 
    WHERE uf.follower_id = u.id
    ) = 0;
    
#------5------

SELECT id, 
	username 
FROM users
ORDER BY id;

#------6------

SELECT u.id,
	u.username
FROM users u
INNER JOIN users_followers uf
ON u.id = uf.user_id
WHERE uf.follower_id = u.id
ORDER BY u.id;

#------7------

SELECT id, 
	path, 
	size
FROM pictures
WHERE size > 50000 AND (path LIKE '%png' OR path LIKE '%jpeg')
ORDER BY size DESC;

#------8------

SELECT c.id, 
	CONCAT(u.username, ' : ', c.content) full_comment
FROM users u
INNER JOIN comments c
ON u.id = c.user_id
ORDER BY c.id DESC;

#------9------

SELECT u.id,
	u.username,
	CONCAT(p.size, 'KB')
FROM users u
INNER JOIN pictures p
ON u.profile_picture_id = p.id
INNER JOIN users d
ON d.profile_picture_id = u.profile_picture_id AND d.id != u.id
GROUP BY u.id
ORDER BY u.id;

#------10------

SELECT p.id, 
	p.caption,
	COUNT(c.id) comments
FROM posts p
INNER JOIN comments c
ON p.id = c.post_id
GROUP BY p.id
ORDER BY comments DESC;

#------11------

SELECT u.id,
	u.username,
    (SELECT COUNT(p.id) FROM posts p WHERE p.user_id = u.id) posts,
    COUNT(uf.follower_id) followers
FROM users u
INNER JOIN users_followers uf
ON uf.user_id = u.id
GROUP BY u.id
ORDER BY followers DESC
LIMIT 1;

#------12------

SELECT u.id,
	u.username,
    SUM((SELECT COUNT(c.id) FROM comments c WHERE c.post_id = p.id AND u.id = c.user_id)) my_comments
FROM users u
LEFT JOIN posts p
ON p.user_id = u.id
GROUP BY u.id
ORDER BY my_comments DESC;

#------13------

SELECT u.id, 
	u.username, 
	(SELECT p.caption
	FROM posts p
	LEFT JOIN comments c
	ON p.id = c.post_id
	WHERE p.user_id = u.id
	GROUP BY p.id
	ORDER BY COUNT(*) DESC, p.id
	LIMIT 1) caption
FROM users u
#WHERE caption IS NOT NULL - no idea why it doesnt work
WHERE (SELECT COUNT(pi.id) FROM posts pi WHERE pi.user_id = u.id) > 0
ORDER BY u.id;

#------14------

SELECT p.id, p.caption, COUNT(DISTINCT c.user_id) comments
FROM posts p
LEFT JOIN comments c
ON c.post_id = p.id
GROUP BY p.id
ORDER BY comments DESC, p.id

#------15------

DELIMITER //

CREATE PROCEDURE udp_commit(username VARCHAR(30), 
	password VARCHAR(30), caption VARCHAR(255), path VARCHAR(255))
BEGIN
	IF (
		(SELECT u.username 
        FROM users u 
        WHERE u.username = username AND u.password = password) 
        IS NULL)
	THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Password is incorrect!';
    ELSEIF (
		(SELECT p.path FROM pictures p 
        WHERE p.path = path) 
        IS NULL)
    THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'The picture does not exist!';
    END IF;
    
    INSERT INTO posts(caption, user_id, picture_id)
    VALUES(
		caption,
		(SELECT u.id FROM users u WHERE u.username = username AND u.password = password),
        (SELECT p.id FROM pictures p WHERE p.path = path)
        );
END//

#------16------
CREATE PROCEDURE udp_filter(hashtag VARCHAR(30))
BEGIN
	SELECT p.id, 
		p.caption, 
		u.username
    FROM posts p
    INNER JOIN users u
    ON p.user_id = u.id
    WHERE p.caption LIKE CONCAT('%#', hashtag, '%')
    ORDER BY p.id; 
END//

CALL udp_filter('cool');


