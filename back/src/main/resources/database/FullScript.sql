-- ************************** Tables : **************************
DROP TABLE IF EXISTS questions CASCADE;
CREATE TABLE questions(
                          id_question SERIAL PRIMARY KEY,
                          description VARCHAR(400)
);

DROP TABLE IF EXISTS answers CASCADE;
CREATE TABLE answers(
                        id_answer SERIAL PRIMARY KEY,
                        description VARCHAR(400)
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles(
                      id_role SERIAL PRIMARY KEY,
                      role_name VARCHAR(50)
);

DROP TABLE IF EXISTS habits CASCADE;
CREATE TABLE habits(
                       id_habit SERIAL PRIMARY KEY,
                       description VARCHAR(400)
);

DROP TABLE IF EXISTS initiales_questions CASCADE;
CREATE TABLE initiales_questions(
                                    id_question SERIAL PRIMARY KEY,
                                    id_answer INT NOT NULL,
                                    FOREIGN KEY(id_question) REFERENCES questions(id_question),
                                    FOREIGN KEY(id_answer) REFERENCES answers(id_answer)
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
                      id_user SERIAL PRIMARY KEY,
                      login VARCHAR(50),
                      password VARCHAR(50),
                      id_role INT NOT NULL,
                      connected BOOLEAN DEFAULT FALSE,
                      FOREIGN KEY(id_role) REFERENCES roles(id_role)
);

DROP TABLE IF EXISTS consumption_values CASCADE;
CREATE TABLE consumption_values(
                                   id_value SERIAL PRIMARY KEY,
                                   description VARCHAR(50),
                                   value INT
);

-- ************************** Associations : **************************
DROP TABLE IF EXISTS question_to_answers CASCADE;
CREATE TABLE question_to_answers(
                                    id_question INT,
                                    id_answer INT,
                                    PRIMARY KEY(id_question, id_answer),
                                    FOREIGN KEY(id_question) REFERENCES questions(id_question),
                                    FOREIGN KEY(id_answer) REFERENCES answers(id_answer)
);

DROP TABLE IF EXISTS habit_to_questions CASCADE;
CREATE TABLE habit_to_questions(
                                   id_question INT,
                                   id_habit INT,
                                   PRIMARY KEY(id_question, id_habit),
                                   FOREIGN KEY(id_question) REFERENCES questions(id_question),
                                   FOREIGN KEY(id_habit) REFERENCES habits(id_habit)
);

DROP TABLE IF EXISTS user_to_answers_to_question CASCADE;
CREATE TABLE user_to_answers_to_question(
                                            id_question INT,
                                            id_answer INT,
                                            id_user INT,
                                            PRIMARY KEY(id_question, id_answer, id_user),
                                            FOREIGN KEY(id_question) REFERENCES questions(id_question),
                                            FOREIGN KEY(id_answer) REFERENCES answers(id_answer),
                                            FOREIGN KEY(id_user) REFERENCES users(id_user)
);

DROP TABLE IF EXISTS questions_launches_habit CASCADE;
CREATE TABLE questions_launches_habit(
                                         id_habit INT,
                                         id_question INT,
                                         PRIMARY KEY(id_habit, id_question),
                                         FOREIGN KEY(id_habit) REFERENCES habits(id_habit),
                                         FOREIGN KEY(id_question) REFERENCES initiales_questions(id_question)
);

-- ************************** Inserting values : **************************
-- Roles :
INSERT INTO roles(role_name) VALUES ('ADMIN');
INSERT INTO roles(role_name) VALUES ('EXPERT');
INSERT INTO roles(role_name) VALUES ('USER');

-- Users :
INSERT INTO users(login,password,id_role) VALUES ('admin','admin',1);
INSERT INTO users(login,password,id_role) VALUES ('expert','expert',2);
INSERT INTO users(login,password,id_role) VALUES ('user','user',3);
INSERT INTO users(login,password,id_role) VALUES ('test_user','user2',3);

-- Habits :
INSERT INTO habits(description) VALUES ('Transport'); -- 1
INSERT INTO habits(description) VALUES ('Alimentation'); -- 2
INSERT INTO habits(description) VALUES ('Logement'); -- 3
INSERT INTO habits(description) VALUES ('Numérique'); -- 4

-- Questions :
INSERT INTO questions(description) VALUES ('Prenez vous la voiture ?'); -- 1
INSERT INTO answers(description) VALUES ('Oui'); -- 1
INSERT INTO answers(description) VALUES ('Non'); -- 2
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,1);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,2);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (1,1);
INSERT INTO initiales_questions(id_question, id_answer) VALUES (1,1);
INSERT INTO questions_launches_habit(id_habit,id_question) VALUES (1,1);

INSERT INTO questions(description) VALUES ('Quel type de moteur utilise votre voiture ?'); --2
INSERT INTO answers(description) VALUES ('Essence'); -- 3
INSERT INTO answers(description) VALUES ('Diesel'); -- 4
INSERT INTO answers(description) VALUES ('Electrique'); -- 5
INSERT INTO answers(description) VALUES ('Hybride'); -- 6
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,3);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,4);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,5);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,6);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (2,1);

INSERT INTO questions(description) VALUES ('Quel genre de voiture avez vous ?'); --3
INSERT INTO answers(description) VALUES ('4x4'); -- 7
INSERT INTO answers(description) VALUES ('SUV'); -- 8
INSERT INTO answers(description) VALUES ('Voiture de ville'); -- 9
INSERT INTO question_to_answers(id_question,id_answer) VALUES (3,7);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (3,8);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (3,9);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (3,1);

INSERT INTO questions(description) VALUES ('Quelle distance parcourez vous par jour ?'); --4
INSERT INTO answers(description) VALUES ('1km'); -- 10
INSERT INTO answers(description) VALUES ('5km'); -- 11
INSERT INTO answers(description) VALUES ('10km'); -- 12
INSERT INTO answers(description) VALUES ('20km'); -- 13
INSERT INTO answers(description) VALUES ('40km'); -- 14
INSERT INTO answers(description) VALUES ('50km+'); -- 15
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,10);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,11);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,12);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,13);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,14);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (4,15);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (4,1);


INSERT INTO questions(description) VALUES ('A quel fréquence consommer vous du lait/ou des plats contentant du lait ?'); --5
INSERT INTO answers(description) VALUES ('Jamais'); -- 16
INSERT INTO answers(description) VALUES ('1 fois par jour'); -- 17
INSERT INTO answers(description) VALUES ('2 fois par jour'); -- 18
INSERT INTO answers(description) VALUES ('3 fois par jour'); -- 19
INSERT INTO answers(description) VALUES ('4 ou + par jour'); -- 20
INSERT INTO question_to_answers(id_question,id_answer) VALUES (5,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (5,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (5,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (5,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (5,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (5,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous de la viande ou des plats à base de viande ?'); --6
INSERT INTO question_to_answers(id_question,id_answer) VALUES (6,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (6,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (6,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (6,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (6,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (6,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous des produits contenant des céréales ?'); --7
INSERT INTO question_to_answers(id_question,id_answer) VALUES (7,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (7,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (7,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (7,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (7,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (7,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous des fruits de saison ?'); --8
INSERT INTO question_to_answers(id_question,id_answer) VALUES (8,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (8,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (8,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (8,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (8,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (8,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous des fruits hors saison ?'); --9
INSERT INTO question_to_answers(id_question,id_answer) VALUES (9,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (9,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (9,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (9,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (9,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (9,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous des légumes de saison ?'); --10
INSERT INTO question_to_answers(id_question,id_answer) VALUES (10,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (10,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (10,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (10,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (10,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (10,2);

INSERT INTO questions(description) VALUES ('A quel fréquence consommez vous des légumes hors saison ?'); --11
INSERT INTO question_to_answers(id_question,id_answer) VALUES (11,16);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (11,17);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (11,18);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (11,19);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (11,20);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (11,2);


INSERT INTO questions(description) VALUES ('Consommez vous du bio au quotidien?'); --12
INSERT INTO answers(description) VALUES ('Oui'); -- 21
INSERT INTO answers(description) VALUES ('Non'); -- 22
INSERT INTO question_to_answers(id_question,id_answer) VALUES (12,21);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (12,22);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (12,2);

INSERT INTO questions(description) VALUES ('Comment vous chauffez vous ?'); --13
INSERT INTO answers(description) VALUES ('Chauffage au bois'); -- 23
INSERT INTO answers(description) VALUES ('Electrique'); -- 24
INSERT INTO answers(description) VALUES ('Gaz'); -- 25
INSERT INTO question_to_answers(id_question,id_answer) VALUES (13,23);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (13,24);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (13,25);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (13,3);

INSERT INTO questions(description) VALUES ('Combien votre logement possède-t-il de pièce ?'); --14
INSERT INTO answers(description) VALUES ('1 pièce'); -- 26
INSERT INTO answers(description) VALUES ('2 pièces'); -- 27
INSERT INTO answers(description) VALUES ('3 pièces'); -- 28
INSERT INTO answers(description) VALUES ('4 pièces'); -- 29
INSERT INTO answers(description) VALUES ('5 pièces ou +'); -- 30
INSERT INTO question_to_answers(id_question,id_answer) VALUES (14,26);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (14,27);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (14,28);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (14,29);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (14,30);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (14,3);

INSERT INTO questions(description) VALUES ('Combien de mois chauffez vous par an ?'); --15
INSERT INTO answers(description) VALUES ('1 mois'); -- 31
INSERT INTO answers(description) VALUES ('2 à 3 mois'); -- 32
INSERT INTO answers(description) VALUES ('4 à 5 mois'); -- 33
INSERT INTO answers(description) VALUES ('Plus de 6 mois'); -- 34
INSERT INTO question_to_answers(id_question,id_answer) VALUES (15,31);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (15,32);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (15,33);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (15,34);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (15,3);


INSERT INTO questions(description) VALUES ('Utilisez vous un ordinateur ?'); --16
INSERT INTO answers(description) VALUES ('Oui'); -- 35
INSERT INTO answers(description) VALUES ('Non'); -- 36
INSERT INTO question_to_answers(id_question,id_answer) VALUES (16,35);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (16,36);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (16,4);
INSERT INTO initiales_questions(id_question, id_answer) VALUES (16,35);
INSERT INTO questions_launches_habit(id_habit,id_question) VALUES (4,16);

INSERT INTO questions(description) VALUES ('Quel genre de PC possédez vous ?'); --17
INSERT INTO answers(description) VALUES ('PC de bureautique'); -- 37
INSERT INTO answers(description) VALUES ('PC gaming'); -- 38
INSERT INTO question_to_answers(id_question,id_answer) VALUES (17,37);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (17,38);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (17,4);

INSERT INTO questions(description) VALUES ('Combien de temps en moyenne utilisez vous le PC chaque jour ?'); --18
INSERT INTO answers(description) VALUES ('Moins de 1 heure'); -- 39
INSERT INTO answers(description) VALUES ('1 à 2 heures'); -- 40
INSERT INTO answers(description) VALUES ('2 à 3 heures'); -- 41
INSERT INTO answers(description) VALUES ('3 à 5 heures'); -- 42
INSERT INTO answers(description) VALUES ('6 à 8 heures'); -- 43
INSERT INTO answers(description) VALUES ('Plus de 8 heures'); -- 44
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,39);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,40);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,41);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,42);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,43);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (18,44);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (18,4);


INSERT INTO consumption_values(description,value) VALUES ('Oui',0.00); -- 1
INSERT INTO consumption_values(description,value) VALUES ('Non',0.00); -- 1
-- Unite : kilos de CO2 pour 1 litre
INSERT INTO consumption_values(description,value) VALUES ('Essence',3.28); -- 1
INSERT INTO consumption_values(description,value) VALUES ('Diesel',2.70); -- 2
INSERT INTO consumption_values(description,value) VALUES ('Electrique',1.33); -- 3
INSERT INTO consumption_values(description,value) VALUES ('Hybride',2); -- 4
-- Prend comme des coef
INSERT INTO consumption_values(description,value) VALUES ('4x4',3);
INSERT INTO consumption_values(description,value) VALUES ('SUV',1);
INSERT INTO consumption_values(description,value) VALUES ('Voiture de ville',2);
-- Prend comme des coef
INSERT INTO consumption_values(description,value) VALUES ('1km',4);
INSERT INTO consumption_values(description,value) VALUES ('5km',4);
INSERT INTO consumption_values(description,value) VALUES ('10km',4);
INSERT INTO consumption_values(description,value) VALUES ('20km',4);
INSERT INTO consumption_values(description,value) VALUES ('40km',4);
INSERT INTO consumption_values(description,value) VALUES ('50km+',4);

INSERT INTO consumption_values(description,value) VALUES ('Jamais',0.00); -- 1
-- Des valeurs direct en kg
-- INSERT INTO consumption_values(description,value) VALUES ("lait 1 fois par jour",1,29);
-- INSERT INTO consumption_values(description,value) VALUES ("viand 1 fois par jour",15);
-- INSERT INTO consumption_values(description,value) VALUES ("fruits de saison 1 fois par jour",5);
-- INSERT INTO consumption_values(description,value) VALUES ("fruits hors saison 1 fois par jour",10);
-- INSERT INTO consumption_values(description,value) VALUES ("légumes de saison 1 fois par jour",5);
-- INSERT INTO consumption_values(description,value) VALUES ("légumes hors saison 1 fois par jour",10);
INSERT INTO consumption_values(description,value) VALUES ('1 fois par jour',1);
INSERT INTO consumption_values(description,value) VALUES ('2 fois par jour',2);
INSERT INTO consumption_values(description,value) VALUES ('3 fois par jour',3);
INSERT INTO consumption_values(description,value) VALUES ('4 ou + par jour',4);

-- j'ai les ajouter demande moi a expliquer
INSERT INTO consumption_values(description,value) VALUES ('Oui',1.00); -- 1
INSERT INTO consumption_values(description,value) VALUES ('Non',0.00); -- 1
--
INSERT INTO consumption_values(description,value) VALUES ('Chauffage au bois',4.1);
INSERT INTO consumption_values(description,value) VALUES ('Electrique',4.1);
INSERT INTO consumption_values(description,value) VALUES ('Gaz',4.1);
INSERT INTO consumption_values(description,value) VALUES ('1 pièce',4.1);
INSERT INTO consumption_values(description,value) VALUES ('2 pièces',4.1);
INSERT INTO consumption_values(description,value) VALUES ('3 pièces',4.1);
INSERT INTO consumption_values(description,value) VALUES ('4 pièces',4.1);
INSERT INTO consumption_values(description,value) VALUES ('5 pièces ou +',4.1);
INSERT INTO consumption_values(description,value) VALUES ('1 mois',4.1);
INSERT INTO consumption_values(description,value) VALUES ('2 à 3 mois',4.1);
INSERT INTO consumption_values(description,value) VALUES ('4 à 5 mois',4.1);
INSERT INTO consumption_values(description,value) VALUES ('Plus de 6 mois',4.1);

INSERT INTO consumption_values(description,value) VALUES ('Oui',1.00);
INSERT INTO consumption_values(description,value) VALUES ('Non',0.00);

INSERT INTO consumption_values(description,value) VALUES ('PC de bureautique',4.1);
INSERT INTO consumption_values(description,value) VALUES ('PC gaming',4.1);

INSERT INTO consumption_values(description,value) VALUES ('Moins de 1 heure',4.1);
INSERT INTO consumption_values(description,value) VALUES ('1 à 2 heures',4.1);
INSERT INTO consumption_values(description,value) VALUES ('2 à 3 heures',4.1);
INSERT INTO consumption_values(description,value) VALUES ('3 à 5 heures',4.1);
INSERT INTO consumption_values(description,value) VALUES ('6 à 8 heures',4.1);
INSERT INTO consumption_values(description,value) VALUES ('Plus de 8 heures',4.1);

