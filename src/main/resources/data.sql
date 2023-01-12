SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `lection_9-11`.genre;
TRUNCATE TABLE `lection_9-11`.book;


INSERT INTO `lection_9-11`.genre
VALUES (1, 'Technical'),
       (2, 'Realism'),
       (3, 'Fiction'),
       (4, 'Horror');

INSERT INTO `lection_9-11`.book
VALUES (1, 'Java 8 in Action', 'Raoul-Gabriel Urma, Mario Fusco, and Alan Mycroft', 1, 424),
       (2, 'Spring in Action', 'Craig Walls', 1, 498),
       (3, 'Harry Potter and the Philosopher''s Stone', 'J. K. Rowling', 3, 223),
       (4, 'The Night in Lisbon', 'Erich Remark', 2, 258),
       (5, 'Arch of Triumph', 'Erich Remark', 2, 455),
       (6, 'It', 'Steven King', 4, 1138);