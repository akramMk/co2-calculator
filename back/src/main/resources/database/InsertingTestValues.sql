-- Roles :
INSERT INTO roles(role_name) VALUES ('ADMIN');
INSERT INTO roles(role_name) VALUES ('EXPERT');
INSERT INTO roles(role_name) VALUES ('USER');

-- Questions :
INSERT INTO questions(description) VALUES ('What is the make and model of your car?'); -- 1
INSERT INTO questions(description) VALUES ('What is your average daily mileage using your car?'); -- 2
INSERT INTO questions(description) VALUES ('Do you have a car?'); -- 3

-- Answers :
INSERT INTO answers(description) VALUES ('Audi A3'); -- 1
INSERT INTO answers(description) VALUES ('Renault Clio'); -- 2
INSERT INTO answers(description) VALUES ('Peugeot 208'); -- 3
INSERT INTO answers(description) VALUES ('Volkswagen Golf'); -- 4
INSERT INTO answers(description) VALUES ('Less than 10 km'); -- 5
INSERT INTO answers(description) VALUES ('Between 10 and 50 km'); -- 6
INSERT INTO answers(description) VALUES ('Between 50 and 100 km'); -- 7
INSERT INTO answers(description) VALUES ('More than 100 km'); -- 8
INSERT INTO answers(description) VALUES ('Yes'); -- 9
INSERT INTO answers(description) VALUES ('No'); -- 10

-- Questions to answers :
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,1);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,2);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,3);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (1,4);

INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,5);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,6);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,7);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (2,8);

INSERT INTO question_to_answers(id_question,id_answer) VALUES (3,9);
INSERT INTO question_to_answers(id_question,id_answer) VALUES (3,10);


-- Habits :
INSERT INTO habits(description) VALUES ('You are a good driver'); -- 1

-- Habits to questions :
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (1,1);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (2,1);
INSERT INTO habit_to_questions(id_question,id_habit) VALUES (3,1);

-- Users :
INSERT INTO users(login,password,id_role) VALUES ('admin','admin',1);
INSERT INTO users(login,password,id_role) VALUES ('expert','expert',2);
INSERT INTO users(login,password,id_role) VALUES ('user','user',3);
INSERT INTO users(login,password,id_role) VALUES ('test_user','user2',3);

-- User to answers to question :
INSERT INTO user_to_answers_to_question(id_question,id_answer,id_user) VALUES (1,1,4);
INSERT INTO user_to_answers_to_question(id_question,id_answer,id_user) VALUES (1,2,4);
INSERT INTO user_to_answers_to_question(id_question,id_answer,id_user) VALUES (2,6,4);

-- Initial questions :
INSERT INTO initiales_questions(id_question, id_answer) VALUES (3,9);

-- Questions launches habit :
INSERT INTO questions_launches_habit(id_habit,id_question) VALUES (1,3);