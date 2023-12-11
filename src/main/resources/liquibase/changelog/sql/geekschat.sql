-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema geekschat
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema geekschat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `geekschat` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `geekschat` ;

-- -----------------------------------------------------
-- Table `geekschat`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geekschat`.`users` (
                                                   `user_id` INT NOT NULL AUTO_INCREMENT,
                                                   `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `email` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `geekschat`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geekschat`.`contacts` (
                                                      `contact_id` INT NOT NULL AUTO_INCREMENT,
                                                      `user_id` INT NULL DEFAULT NULL,
                                                      `contact_user_id` INT NULL DEFAULT NULL,
                                                      PRIMARY KEY (`contact_id`),
    INDEX `user_id` (`user_id` ASC) VISIBLE,
    INDEX `contact_user_id` (`contact_user_id` ASC) VISIBLE,
    CONSTRAINT `Contacts_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `geekschat`.`users` (`user_id`),
    CONSTRAINT `Contacts_ibfk_2`
    FOREIGN KEY (`contact_user_id`)
    REFERENCES `geekschat`.`users` (`user_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
