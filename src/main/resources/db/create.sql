SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS epicodus (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    address VARCHAR,
    zipcode VARCHAR,
    phone VARCHAR,
    email VARCHAR,
    lastjob VARCHAR,
    age INTEGER
    );

CREATE TABLE IF NOT EXISTS students (
    id int PRIMARY KEY auto_increment,
    name VARCHAR
    );


CREATE TABLE IF NOT EXISTS foreignstudents (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    country VARCHAR
    );

CREATE TABLE IF NOT EXISTS tracks (
    id int PRIMARY KEY auto_increment,
    ratedBy VARCHAR,
    rating int,
    epicodusId INTEGER,
    content VARCHAR
    );

CREATE TABLE IF NOT EXISTS epicodus_students (
 id int PRIMARY KEY auto_increment,
 epicodusId INTEGER,
 studentId INTEGER
);