# data

# --- !Ups

INSERT INTO m_user VALUES (1, 'root','root');
INSERT INTO m_user VALUES (2, 'root1','root1');
INSERT INTO m_user VALUES (3, 'admin','admin');

INSERT INTO movie VALUES (1, 'интерстеллар', '8.8');
INSERT INTO movie VALUES (2, 'неудержимые', '5.5');
INSERT INTO movie VALUES (3, 'гарри потер', '6.7');
INSERT INTO movie VALUES (4, 'мадагаска', '8.9');
INSERT INTO movie VALUES (5, 'король лев', '5.6');
INSERT INTO movie VALUES (6, 'пираты', '3.8');
INSERT INTO movie VALUES (7, 'зеркала', '9.8');
INSERT INTO movie VALUES (8, 'рокки бальбо', '7.8');
INSERT INTO movie VALUES (9, 'гетсби', '2.8');
INSERT INTO movie VALUES (10, 'титаник', '10');

INSERT INTO favorite VALUES (1, 'favorite_cartoon');
INSERT INTO favorite VALUES (2, 'favorite_list');
INSERT INTO favorite VALUES (3, 'favorite_list_test');

INSERT INTO favorite_movie VALUES (1, 1);
INSERT INTO favorite_movie VALUES (1, 3);
INSERT INTO favorite_movie VALUES (1, 7);
INSERT INTO favorite_movie VALUES (2, 2);
INSERT INTO favorite_movie VALUES (2, 4);
INSERT INTO favorite_movie VALUES (2, 3);
INSERT INTO favorite_movie VALUES (2, 9);
INSERT INTO favorite_movie VALUES (3, 10);
INSERT INTO favorite_movie VALUES (3, 5);
INSERT INTO favorite_movie VALUES (3, 4);

INSERT INTO m_user_favorite VALUES (1, 1);
INSERT INTO m_user_favorite VALUES (1, 2);
INSERT INTO m_user_favorite VALUES (2, 3);

# --- !Downs

DELETE FROM m_user_favorite;
DELETE FROM favorite_movie;
DELETE FROM favorite;
DELETE FROM m_user;
DELETE FROM movie;
