CREATE DATABASE cde1101_daaf_tucay;
USE cde1101_daaf_tucay;

CREATE TABLE questions(
    question_id INT PRIMARY KEY,
    question VARCHAR(255),
    option_1 VARCHAR(255),
    option_2 VARCHAR(255),
    option_3 VARCHAR(255),
    option_4 VARCHAR(255),
    answer VARCHAR(1)
);

DROP TABLE questions;

SELECT * FROM questions;

DELETE FROM questions WHERE question_id BETWEEN 1 AND 30;

INSERT INTO questions (question_id, question, option_1, option_2, option_3, option_4, answer) VALUES
(1, 'What keyword is used to define a constant in Java?', 'final', 'const', 'static', 'immutable', 'A'),
(2, 'Which method is called when an object is created in Python?', '__init__', '__new__', 'constructor', '__main__', 'A'),
(3, 'Which of these is NOT a primitive data type in Java?', 'int', 'float', 'boolean', 'String', 'D'),
(4, 'What is the correct way to start a single-line comment in C#?', '//', '#', '/*', '--', 'A'),
(5, 'Which of the following is used to define a class in Python?', 'class', 'Class', 'def', 'struct', 'A'),
(6, 'How do you declare a pointer in C++?', 'int* p;', 'int p;', 'ptr int;', 'int &p;', 'A'),
(7, 'Which loop is guaranteed to execute at least once in Java?', 'for', 'while', 'do-while', 'foreach', 'C'),
(8, 'Which of these is a correct file extension for Python scripts?', '.py', '.java', '.cs', '.cpp', 'A'),
(9, 'Which keyword in C# is used to handle exceptions?', 'try', 'catch', 'throw', 'All of the above', 'D'),
(10, 'How do you allocate memory dynamically in C++?', 'new', 'malloc', 'alloc', 'create', 'A'),
(11, 'Which keyword is used to inherit a class in Java?', 'extends', 'implements', 'inherits', 'derives', 'A'),
(12, 'What is the output of print(2 ** 3) in Python?', '6', '8', '9', '5', 'B'),
(13, 'What is the default access modifier in C# if none is specified?', 'public', 'private', 'protected', 'internal', 'D'),
(14, 'Which of these can be used to deallocate memory in C++?', 'free', 'delete', 'remove', 'release', 'B'),
(15, 'What is the correct way to declare an interface in Java?', 'interface MyInterface {}', 'class MyInterface {}', 'abstract MyInterface {}', 'struct MyInterface {}', 'A'),
(16, 'How do you read input from the user in Python?', 'input()', 'cin', 'scanf()', 'read()', 'A'),
(17, 'Which of these is not a valid C# data type?', 'string', 'double', 'char', 'character', 'D'),
(18, 'Which operator is used for dereferencing a pointer in C++?', '*', '&', '->', '.', 'A'),
(19, 'Which Java collection maintains elements in insertion order?', 'HashSet', 'TreeSet', 'LinkedHashSet', 'PriorityQueue', 'C'),
(20, 'Which of the following is used for function overloading in Python?', 'Same name, different arguments', 'Different name, same arguments', 'Same name, same arguments', 'Different class, same name', 'A'),
(21, 'What does the "static" keyword do in C#?', 'Defines a constant', 'Makes a variable shared among all instances', 'Marks a method as abstract', 'Creates a local variable', 'B'),
(22, 'Which of these is the correct syntax to create a vector in C++?', 'vector<int> v;', 'list<int> v;', 'array<int> v;', 'set<int> v;', 'A'),
(23, 'How do you define an abstract method in Java?', 'abstract void method();', 'void abstract method();', 'void method() abstract;', 'method() abstract void;', 'A'),
(24, 'Which of these is the correct way to create a dictionary in Python?', 'dict = {}', 'dict = []', 'dict = ()', 'dict = ""', 'A'),
(25, 'What is the default value of an uninitialized integer in C#?', '0', 'null', 'undefined', 'garbage value', 'A'),
(26, 'Which operator is used for pointer-to-member access in C++?', '->', '.', '::', '=>', 'A'),
(27, 'Which keyword is used to prevent method overriding in Java?', 'final', 'static', 'override', 'sealed', 'A'),
(28, 'Which function converts a string into an integer in Python?', 'int()', 'parseInt()', 'convert()', 'strToInt()', 'A'),
(29, 'How do you create a read-only property in C#?', 'Use "get" without "set"', 'Use "const"', 'Use "readonly"', 'Use "static"', 'A'),
(30, 'What does "cout" stand for in C++?', 'Console Output', 'Character Output', 'Class Output', 'Common Output', 'A');

CREATE TABLE users(
    student_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255)
);

DROP TABLE users;

SELECT * FROM users;

INSERT INTO users(student_id, first_name, last_name, password)
VALUES
('123', 'John', 'Doe', 'password123'),
('456', 'Jane', 'Smith', 'password456'),
('789', 'Bob', 'Johnson', 'password789');

SELECT * FROM quiz_sessions;

CREATE TABLE quiz_sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    question_id INT NOT NULL,
    user_answer CHAR(1),
    completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (student_id) REFERENCES users(student_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

ALTER TABLE quiz_sessions ADD COLUMN current_position INT DEFAULT 1;

DROP TABLE quiz_sessions;