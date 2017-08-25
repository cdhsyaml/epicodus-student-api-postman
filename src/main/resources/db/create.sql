SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS epicodus (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    address VARCHAR,
    zipcode VARCHAR,
    phone VARCHAR,
    email VARCHAR,
    );

CREATE TABLE IF NOT EXISTS student (
    id int PRIMARY KEY auto_increment,
    name VARCHAR
    );

CREATE TABLE IF NOT EXISTS tracks (
    id int PRIMARY KEY auto_increment,
    writtenBy VARCHAR,
    rating int,
    epicodusId INTEGER,
    content VARCHAR
    );

CREATE TABLE IF NOT EXISTS epicodus_student (
 id int PRIMARY KEY auto_increment,
 epicodusId INTEGER,
 studentId INTEGER
);