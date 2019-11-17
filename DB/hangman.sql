-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hangman
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hangman` ;

-- -----------------------------------------------------
-- Schema hangman
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hangman` DEFAULT CHARACTER SET utf8 ;
USE `hangman` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `totalPoints` INT NOT NULL DEFAULT 0,
  `preferredModelColor` VARCHAR(45) NULL,
  `preferredDifficulty` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `word` ;

CREATE TABLE IF NOT EXISTS `word` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(255) NOT NULL,
  `difficulty` VARCHAR(25) NULL COMMENT '0 = default/unknown\n1 = easy\n2 = medium\n3 = hard\n4 = impossible\n\nWe could run some sort of calculation to dictate this, like the ratio of consonants to vowels and/or ratio of unique letters',
  `syllables` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `game` ;

CREATE TABLE IF NOT EXISTS `game` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `word_id` INT NOT NULL,
  `gameWon` TINYINT NULL DEFAULT 0,
  `pointsAwarded` INT NULL DEFAULT 0,
  `gameDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `word_id_idx` (`word_id` ASC),
  CONSTRAINT `game_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `game_word`
    FOREIGN KEY (`word_id`)
    REFERENCES `word` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `definition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `definition` ;

CREATE TABLE IF NOT EXISTS `definition` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `word_id` INT NOT NULL,
  `definition` TEXT NULL,
  `partOfSpeech` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `definition_word_idx` (`word_id` ASC),
  CONSTRAINT `definition_word`
    FOREIGN KEY (`word_id`)
    REFERENCES `word` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `example`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `example` ;

CREATE TABLE IF NOT EXISTS `example` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `definition_id` INT NOT NULL,
  `sentence` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `example_definition_idx` (`definition_id` ASC),
  CONSTRAINT `example_definition`
    FOREIGN KEY (`definition_id`)
    REFERENCES `definition` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `secret_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `secret_question` ;

CREATE TABLE IF NOT EXISTS `secret_question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_secret_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_secret_question` ;

CREATE TABLE IF NOT EXISTS `user_secret_question` (
  `question_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `answer` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`, `user_id`),
  INDEX `fk_secret_question_id_idx` (`question_id` ASC),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_secret_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `secret_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS hangmanuser;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'hangmanuser' IDENTIFIED BY 'hangmanuser';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'hangmanuser';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `user` (`id`, `username`, `password`, `totalPoints`, `preferredModelColor`, `preferredDifficulty`) VALUES (1, 'paulo', 'paulo', 0, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `totalPoints`, `preferredModelColor`, `preferredDifficulty`) VALUES (2, 'tim', 'tim', 0, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `totalPoints`, `preferredModelColor`, `preferredDifficulty`) VALUES (3, 'ruhan', 'ruhan', 0, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `totalPoints`, `preferredModelColor`, `preferredDifficulty`) VALUES (4, 'max', 'max', 0, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `totalPoints`, `preferredModelColor`, `preferredDifficulty`) VALUES (5, 'adam', 'adam', 0, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `word`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `word` (`id`, `word`, `difficulty`, `syllables`) VALUES (1, 'turophobia', 'hard', 5);
INSERT INTO `word` (`id`, `word`, `difficulty`, `syllables`) VALUES (2, 'thither', 'easy', 2);
INSERT INTO `word` (`id`, `word`, `difficulty`, `syllables`) VALUES (3, 'obumbrate', 'easy', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `game`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (1, 1, 1, 0, 0, '2019-02-07 14:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (2, 2, 2, 1, 1, '2019-02-07 16:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (3, 3, 2, 1, 1, '2019-02-07 18:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (4, 4, 3, 1, 1, '2019-02-07 20:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (5, 5, 3, 1, 1, '2019-02-07 22:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (6, 1, 3, 0, 0, '2019-02-07 15:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (7, 2, 1, 0, 0, '2019-02-07 17:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (8, 3, 1, 1, 3, '2019-02-07 19:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (9, 4, 2, 1, 1, '2019-02-07 21:00:00');
INSERT INTO `game` (`id`, `user_id`, `word_id`, `gameWon`, `pointsAwarded`, `gameDate`) VALUES (10, 5, 2, 1, 1, '2019-02-07 23:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `definition`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `definition` (`id`, `word_id`, `definition`, `partOfSpeech`) VALUES (1, 1, 'an irrational or disproportionate fear of cheese', 'noun');
INSERT INTO `definition` (`id`, `word_id`, `definition`, `partOfSpeech`) VALUES (2, 2, 'to or toward that place or point; there.\n', 'adverb');
INSERT INTO `definition` (`id`, `word_id`, `definition`, `partOfSpeech`) VALUES (3, 2, 'on the farther or other side or in the direction away from the person speaking; farther; more remote.', 'adjective');
INSERT INTO `definition` (`id`, `word_id`, `definition`, `partOfSpeech`) VALUES (4, 3, 'to darken, overshadow, or cloud.', 'verb');

COMMIT;


-- -----------------------------------------------------
-- Data for table `example`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `example` (`id`, `definition_id`, `sentence`) VALUES (1, 1, 'What is your main character’s worst fear? Is it something universal, like the death of a loved one? Or a rare phobia, like __________ (fear of cheese).');
INSERT INTO `example` (`id`, `definition_id`, `sentence`) VALUES (2, 2, 'We told them that we were travelling, that we had been transported _______, and that they had nothing to fear from us.');
INSERT INTO `example` (`id`, `definition_id`, `sentence`) VALUES (3, 2, 'He was a thorough-going old Tory ... who seldom himself went near the metropolis, unless called _______ by some occasion of cattle-showing.');
INSERT INTO `example` (`id`, `definition_id`, `sentence`) VALUES (4, 3, '... that solemn interval of time when the gloom of midnight __________ the globe ....');
INSERT INTO `example` (`id`, `definition_id`, `sentence`) VALUES (5, 3, 'It requires no stretch of mind to conceive that a man placed in a corner of Germany may be every whit as pragmatical and self-important as another man placed in Newhaven, and withal as liable to confound and _________ every subject that may fall his way ....');

COMMIT;


-- -----------------------------------------------------
-- Data for table `secret_question`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `secret_question` (`id`, `question`) VALUES (1, ' What is your favorite movie?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (2, ' What street did you grow up on?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (3, ' Favorite color?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (4, ' Favorite web browser?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (5, ' Favorite website?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (6, ' Favorite forum?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (7, ' Favorite social media website?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (8, ' What is the name of your first school?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (9, ' What is the name of your favorite pet?');
INSERT INTO `secret_question` (`id`, `question`) VALUES (10, ' Who is your favorite actor');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_secret_question`
-- -----------------------------------------------------
START TRANSACTION;
USE `hangman`;
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (1,  1, ' Ender\'s Game');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (2,  1, ' UMUC Rd');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (3,  1, ' Black');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (3,  2, ' Black');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (5,  2, ' reddit.com');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (7,  2, ' reddit.com');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (8,  3, ' UMUC');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (9,  3, ' Steve');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (10,  3, ' Metallica');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (4,  4, ' Safari');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (5,  4, ' reddit.com');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (6,  4, ' reddit.com');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (1,  5, ' Bambi');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (5,  5, ' reddit.com');
INSERT INTO `user_secret_question` (`question_id`, `user_id`, `answer`) VALUES (6,  5, ' reddit.com');

COMMIT;

