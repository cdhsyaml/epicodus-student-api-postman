package dao;

import models.Epicodus;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oEpicodusDaoTest {
    private Sql2oEpicodusDao epicodusDao;
    private Sql2oStudentDao studentDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        epicodusDao = new Sql2oEpicodusDao(sql2o);
        studentDao = new Sql2oStudentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingSetsId() throws Exception {
        Epicodus epicodus = setupNewEpicodus();
        int epicodusId = epicodus.getId();
        epicodusDao.add(epicodus);
        assertNotEquals(epicodusId, epicodus.getId());
    }

    @Test
    public void canFindEpicodusById() throws Exception {
        Epicodus epicodus = setupNewEpicodus();
        epicodusDao.add(epicodus);
        Epicodus foundEpicodus = epicodusDao.findById(epicodus.getId());
        assertEquals(epicodus, foundEpicodus);
    }

    @Test
    public void returnAllAddedEpicodus() throws Exception {
        Epicodus epicodus = setupNewEpicodus();
        epicodusDao.add(epicodus);
        assertEquals(1, epicodusDao.getAll().size());
    }

    @Test
    public void noEpicodussReturnsIfEmpty() throws Exception {
        assertEquals(0, epicodusDao.getAll().size());
    }

    @Test
    public void updateEpicodusInfo() throws Exception {
        String initialName = "Pho Van";
        Epicodus epicodus = setupNewEpicodus();
        epicodusDao.add(epicodus);

        epicodusDao.update(epicodus.getId(),"Panda Express", "1234 SE Division Street", "97206", "503-260-9999", "pho@gmail.com");
        Epicodus updatedRestaurant = epicodusDao.findById(epicodus.getId());
        assertNotEquals(initialName, updatedRestaurant.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectEpicodus() throws Exception {
        Epicodus restaurant = setupNewEpicodus();
        epicodusDao.add(restaurant);
        epicodusDao.deleteById(restaurant.getId());
        assertEquals(0, epicodusDao.getAll().size());
    }

    @Test
    public void clearAllRestaurants(){
        Epicodus epicodus = setupNewEpicodus();
        Epicodus epicodus2 = setupNewEpicodusTwo();
        epicodusDao.add(epicodus);
        epicodusDao.add(epicodus2);
        int daoSize = epicodusDao.getAll().size();
        epicodusDao.clearAllEpicodus();
        assertTrue(daoSize > 0 && daoSize >epicodusDao.getAll().size());

    }

    @Test
    public void getAllStudentsForAnEpicodusReturnsStudentCorrectly() throws Exception {
        Student teststudent  = new Student("Shyamal");
        studentDao.add(teststudent);

        Student otherStudent  = new Student("Mona");
        studentDao.add(otherStudent);

        Epicodus testEpicodus = setupNewEpicodus();
        epicodusDao.add(testEpicodus);
        epicodusDao.addEpicodusToStudent(testEpicodus,teststudent);
        epicodusDao.addEpicodusToStudent(testEpicodus,otherStudent);

        Student[] students = {teststudent, otherStudent};

        assertEquals(epicodusDao.getAllStudentsForAEpicodus(testEpicodus.getId()), Arrays.asList(students));
    }

    @Test
    public void deleteingRestaurantAlsoUpdatesJoinTable() throws Exception {
        Student student  = new Student("Shyamal");
        studentDao.add(student);

        Epicodus epicodus = setupNewEpicodus();
        epicodusDao.add(epicodus);

        Epicodus altEpicodus = setupNewEpicodusTwo();
        epicodusDao.add(altEpicodus);

        epicodusDao.addEpicodusToStudent(epicodus,student);
        epicodusDao.addEpicodusToStudent(altEpicodus, student);

        epicodusDao.deleteById(epicodus.getId());
        assertEquals(0, epicodusDao.getAllStudentsForAEpicodus(epicodus.getId()).size());
    }

    public Epicodus setupNewEpicodus(){
        return new Epicodus("PhoVan", "1234 SE Division Street", "97206", "503-260-9999", "pho@gmail.com" );
    }

    public Epicodus setupNewEpicodusTwo(){
        return new Epicodus("Van", "1432 SE Morrison Street", "97106", "303-200-0099", "gym@gmail.com" );
    }

}