CREATE TABLE `users` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`username` VARCHAR(20) UNIQUE NOT NULL,
`password` VARCHAR(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` TIMESTAMP,
`is_deleted` BOOLEAN
);

INSERT INTO `users` (`username`, `password`) VALUES ("ivo24", "qwerty");
INSERT INTO `users` (`username`, `password`, `last_login_time`) VALUES ("cooldude", "123", now());
INSERT INTO `users` (`username`, `password`) VALUES ("kitty", "asdasd");
INSERT INTO `users` (`username`, `password`, `is_deleted`) VALUES ("joe", "123", 1);
INSERT INTO `users` (`username`, `password`, `is_deleted`) VALUES ("joe445", "qwerty", 0);