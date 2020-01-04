CREATE TABLE `people` (
`id` INT PRIMARY KEY UNIQUE AUTO_INCREMENT,
`name` VARCHAR(200) NOT NULL,
`picture` BLOB(2) NULL,
`height` DECIMAL(2) NULL,
`weight` DECIMAL(2) NULL,
`gender` VARCHAR(1) NOT NULL,
`birthdate` DATE NOT NULL,
`biography` TEXT NULL
);

INSERT INTO `people` (`name`, `gender`, `birthdate`) VALUES ("Ivan", "m", "1990-05-07");
INSERT INTO `people` (`name`, `weight`, `gender`, `birthdate`) VALUES ("Ivo", 55, "m", "1980-06-07");
INSERT INTO `people` (`name`, `weight`, `gender`, `birthdate`, `biography`) VALUES ("Pepi", 68.5, "m", "1988-05-04", "Pesho lud");
INSERT INTO `people` (`name`, `gender`, `birthdate`) VALUES ("Didi", "f", "2010-05-07");
INSERT INTO `people` (`name`, `gender`, `height`, `birthdate`) VALUES ("Kiki", "f", 1.95, "2003-05-07");