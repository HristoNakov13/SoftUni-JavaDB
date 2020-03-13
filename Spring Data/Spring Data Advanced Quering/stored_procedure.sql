DELIMITER //
CREATE PROCEDURE udp_get_books_count_by_author_first_last_names(first_name VARCHAR(255), last_name VARCHAR(255))
BEGIN
	SELECT COUNT(b.id)
    FROM authors a
    INNER JOIN books b
    ON a.id = b.author_id
    WHERE a.first_name = first_name AND a.last_name = last_name
    GROUP BY a.id;
END //