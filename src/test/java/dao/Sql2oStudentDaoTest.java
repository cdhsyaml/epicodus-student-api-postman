package dao;

import models.Epicodus;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;


public class Sql2oStudentDaoTest {

    private Sql2oStudentDao studentDao;
    private Connection conn;
    private Sql2oEpicodusDao epicodusDao;

    public Student setupNewStudent() { return new Student("Chinese");}
    public Student setupNewStudent2() { return new Student("American");}

    public Epicodus setupNewEpicodus(){
        return new Epicodus("PhoVan", "1234 SE Division Street", "97206", "503-260-9999", "pho@gmail.com", "Intel", 23 );
    }

    public Epicodus setupNewEpicodusTwo(){
        return new Epicodus("Van", "1432 SE Morrison Street", "97106", "303-200-0099", "gym@gmail.com", "Syntel", 34 );
    }

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

    @Test
    public void addingSetsId() throws Exception {
        Student student = setupNewStudent();
        int origStudentId = student.getId();
        studentDao.add(student);
        assertNotEquals(origStudentId, student.getId());
    }

    @Test
    public void existingStudentCanBeFoundById() throws Exception {
        Student student = setupNewStudent();
        studentDao.add(student);
        Student foundStudent = studentDao.findById(student.getId());
        assertEquals(student, foundStudent);
    }

    @Test
    public void addedStudentsAreReturnedFromGetAll() throws Exception {
        Student student = setupNewStudent();
        studentDao.add(student);
        assertEquals(1, studentDao.getAll().size());
    }

    @Test
    public void noStudentsReturnsEmptyList() throws Exception {
        assertEquals(0, studentDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectStudent() throws Exception {
        Student student = setupNewStudent();
        studentDao.add(student);
        studentDao.deleteById(student.getId());
        assertEquals(0, studentDao.getAll().size());
    }

    @Test
    public void addStudentToEpicodusAddsTypeCorrectly() throws Exception {

        Epicodus epicodus = setupNewEpicodus();

        Epicodus epicodusTwo = setupNewEpicodusTwo();

        epicodusDao.add(epicodus);

        epicodusDao.add(epicodusTwo);

        Student testStudent = setupNewStudent();

        studentDao.add(testStudent);

        studentDao.addStudentToEpicodus(testStudent, epicodus);

        studentDao.addStudentToEpicodus(testStudent, epicodusTwo);

        assertEquals(2, studentDao.getAllEpicodusForAStudent(testStudent.getId()).size());
    }

    @Test
    public void deletingStudentAlsoUpdatesJoinTable() throws Exception {
        Epicodus testEpicodus = setupNewEpicodus();
        epicodusDao.add(testEpicodus);

        Student testStudent  = setupNewStudent();
        studentDao.add(testStudent);

        Student otherStudent = setupNewStudent2();
        studentDao.add(otherStudent);

        studentDao.addStudentToEpicodus(testStudent, testEpicodus);
        studentDao.addStudentToEpicodus(otherStudent, testEpicodus);

        studentDao.deleteById(testStudent.getId());
        assertEquals(0, studentDao.getAllEpicodusForAStudent(testStudent.getId()).size());
    }



}