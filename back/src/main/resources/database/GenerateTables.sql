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