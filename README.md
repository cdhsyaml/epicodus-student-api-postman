# Epicodus Student API

#### _A Epicodus Student extended Web Application for Epicodus Java Week 4 Independent Project, Aug 25, 2017_

#### By _**Shyamal Punekar**_

## Description

This is a program that allows a  to track epicodus and their students average age, gender breakdown, most popular track, zipcode, last job held before enrolling.
Management software with dashboard for a hypothetical epicodus to allow to do the following:
* VIEW all students and each Epicodus's student;
* ADD a student epicodus;
* UPDATE Epicodus details;
* DELETE epicodus if no longer required and delete a student if they no longer required.

## Specifications

* It allows the user to add new student's details to epicodus.
  *  ![picture](/public/img/POST-request-epicodus.png?raw=true)
  *  ![picture](/public/img/GET-request-epicodus.png?raw=true)

* It allows the user to add new students to an existing epicodus.
  *  ![picture](/public/img/POST-request-student.png?raw=true)
  *  ![picture](/public/img/GET-request-student.png?raw=true)



  ## What's Included

```
epicodus-api

├── README.md
├── build.gradle
├── .gitignore
└── src
     ├── main
     │     ├── java
     │     │     ├── App.java
     │     │     ├── Student.java
     │     │     ├── Epicodus.java
     │     │     ├── Tracks.java
     │     └── resources
     │             └── db
     │              └── create.sql
     └── test
           └── java
                 ├── Sql2oEpicodusDaoTest.java
                 ├── Sql2oStudentDaoTest.java
                 └── Sql2oTracksDaoTest.java
```

## Setup/Installation Requirements

You will need [gradle](https://gradle.org/gradle-download/) installed on your device.

* `$ https://github.com/shyamalpunekar/epicodus-student-api-postman`
* In PSQL:
  * CREATE DATABASE epicodus;
  * \c epicodus-student;
  * CREATE TABLE epicodus (id serial PRIMARY KEY, name varchar, address varchar, zipcode varchar, phone varchar, email varchar);
  * CREATE TABLE students (id serial PRIMARY KEY, name varchar);
* _In the cloned repository root directory, run the command 'gradle run'

Using Postman:
* Fire up Postman, and set the HTTP request type to POST, create a record before we can read it.
* Point the URL to localhost:4567/restaurants/new
* Select the radio button marked "raw", and the type to " JSON application/json" in the dropdown menu.
* Select the Body radio button, and copy in JSON below.
* Hit Send!


## Support and contact details

Please feel free to contact shyamal.punekar@gmail.com if you have any questions, issues, concerns, comments or suggestions.

## Technologies Used

* Java
* jUnit
* Gradle
* spark
* Postman
* postgres

### License

_Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.


Copyright (c) 2017 **_Shyamal Punekar_**
