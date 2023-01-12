CREATE DATABASE IF NOT EXISTS `lection_9-11`;

CREATE TABLE IF NOT EXISTS  `book` (
                        `book_id` int NOT NULL AUTO_INCREMENT,
                        `title` varchar(150) NOT NULL,
                        `author` varchar(150) NOT NULL,
                        `genre` int NOT NULL,
                        `pages_number` int NOT NULL,
                        PRIMARY KEY (`book_id`),
                        KEY `genre_id_idx` (`genre`),
                        CONSTRAINT `genre_id` FOREIGN KEY (`genre`) REFERENCES `genre` (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS  `genre` (
                         `genre_id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(45) NOT NULL,
                         PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
