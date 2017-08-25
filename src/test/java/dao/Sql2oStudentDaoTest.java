package dao;

import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oStudentDaoTest {

    private Sql2oStudentDao studentDao;
    private Connection conn;
    private Sql2oEpicodusDao epicodusDao;

    public Student setupNewStudent() { return new Student("Chinese");}
    public Student setupNewStudent2() { return new Student("American");}

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        studentDao = new Sql2oStudentDao(sql2o);
        epicodusDao = new Sql2oEpicodusDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() throws Exception {
        Student student = setupNewStudent();
        int origStudentId = student.getId();
        studentDao.add(student);
        assertNotEquals(origStudentId, student.getId());

    }

}