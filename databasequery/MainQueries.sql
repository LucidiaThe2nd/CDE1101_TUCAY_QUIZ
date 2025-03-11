CREATE DATABASE cde1101_daaf_tucay;
USE cde1101_daaf_tucay;

CREATE TABLE questions(
    question_id INT PRIMARY KEY,
    question VARCHAR(255),
    option_1 VARCHAR(255),
    option_2 VARCHAR(255),
    option_3 VARCHAR(255),
    option_4 VARCHAR(255),
    answer VARCHAR(255)
);

SELECT * FROM questions;

INSERT INTO questions(question_id, question, option_1, option_2, option_3, option_4, answer)
VALUES
(1, 'Which programming language is known as the backbone of web development?', 'Python', 'Java', 'JavaScript', 'C++', 'JavaScript'),  
(2, 'Who created the Python programming language?', 'Guido van Rossum', 'Dennis Ritchie', 'James Gosling', 'Bjarne Stroustrup', 'Guido van Rossum'),  
(3, 'What does SQL stand for?', 'Structured Query Language', 'Simple Query Logic', 'Systematic Query Language', 'Sequential Query Language', 'Structured Query Language'),  
(4, 'Which language is primarily used for building Android apps?', 'Swift', 'Java', 'C#', 'Go', 'Java'),  
(5, 'What does HTML stand for?', 'Hyper Transfer Markup Language', 'Hyper Text Markup Language', 'Hyper Tool Management Language', 'High-level Text Markup Language', 'Hyper Text Markup Language');