USE `paymybuddy` ;

-- RAZ de la database  --
-- ------------------- --
DROP TABLE if exists `bank_account`;
DROP TABLE if exists `friend`;
DROP TABLE if exists `transaction`;
DROP TABLE if exists `transfert`;
DROP TABLE if exists `user`;

-- -----------------------------------------------------
-- Table `paymybuddy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `balance` DOUBLE NOT NULL,
    `email` VARCHAR(255) NULL DEFAULT NULL,
    `firstname` VARCHAR(255) NULL DEFAULT NULL,
    `lastname` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`bank_account` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `rib` VARCHAR(255) NULL DEFAULT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK92iik4jwhk7q385jubl2bc2mm` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FK92iik4jwhk7q385jubl2bc2mm`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`friend` (
    `user_id` INT NOT NULL,
    `user_friend_id` INT NOT NULL,
    INDEX `FK9heyho11lr3ba0f7j46kxgjoe` (`user_friend_id` ASC) VISIBLE,
    INDEX `FK3uu8s7yyof1qmenthngm24hry` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FK3uu8s7yyof1qmenthngm24hry`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`id`),
    CONSTRAINT `FK9heyho11lr3ba0f7j46kxgjoe`
    FOREIGN KEY (`user_friend_id`)
    REFERENCES `paymybuddy`.`user` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`transaction` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `amount` DOUBLE NULL DEFAULT NULL,
    `date_transaction` DATE NULL DEFAULT NULL,
    `taxe` DOUBLE NULL DEFAULT NULL,
    `receiver_id` INT NOT NULL,
    `sender_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKey21a233t8tlwfsbs228q3b2u` (`receiver_id` ASC) VISIBLE,
    INDEX `FKjpter5yuohdb58gyg6k5nympt` (`sender_id` ASC) VISIBLE,
    CONSTRAINT `FKey21a233t8tlwfsbs228q3b2u`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `paymybuddy`.`user` (`id`),
    CONSTRAINT `FKjpter5yuohdb58gyg6k5nympt`
    FOREIGN KEY (`sender_id`)
    REFERENCES `paymybuddy`.`user` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`transfert`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`transfert` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `amount` DOUBLE NULL DEFAULT NULL,
    `date_transfert` DATE NULL DEFAULT NULL,
    `type` VARCHAR(255) NULL DEFAULT NULL,
    `bank_account_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKeahtdya33ldiq73777e9loh9q` (`bank_account_id` ASC) VISIBLE,
    CONSTRAINT `FKeahtdya33ldiq73777e9loh9q`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `paymybuddy`.`bank_account` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
