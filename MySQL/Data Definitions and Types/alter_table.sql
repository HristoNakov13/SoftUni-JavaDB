ALTER TABLE `minions`
ADD COLUMN `town_id` INT NOT NULL;

ALTER TABLE `minions`
ADD CONSTRAINT fk_minions_town FOREIGN KEY (`town_id`) REFERENCES `towns`(`id`);