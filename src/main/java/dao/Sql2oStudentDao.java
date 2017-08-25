package dao;

import models.Epicodus;
import models.Student;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oStudentDao implements StudentDao {

    private final Sql2o sql2o;

    public Sql2oStudentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO students (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", student.getName())
                    .addColumnMapping("NAME", "name")
                    .executeUpdate()
                    .getKey();
            student.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addStudentToEpicodus(Student student, Epicodus epicodus) {
        String sql = "INSERT INTO epicodus_students (epicodusId, studentId) VALUES (:epicodusId, :studentId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("epicodusId", epicodus.getId())
                    .addParameter("studentId", student.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Student findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM students WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Student.class);
        }
    }

    @Override
    public List<Student> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM students")
                    .executeAndFetch(Student.class);

        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from students WHERE id = :id";
        String deleteJoin = "DELETE from epicodus_students WHERE studentId = :studentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("studentId", id)
                    .executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Epicodus> getAllEpicodusForAStudent(int studentId) {
        List<Epicodus> epicoduses = new ArrayList();
        String joinQuery = "SELECT epicodusId FROM epicodus_students WHERE studentId = :studentId";

        try (Connection con = sql2o.open()) {
            List<Integer> allEpicodusIds = con.createQuery(joinQuery)
                    .addParameter("studentId", studentId)
                    .executeAndFetch(Integer.class);
            for (Integer epicodusId : allEpicodusIds){
                String epicodusQuery = "SELECT * FROM epicodus WHERE id = :epicodusId";
                epicoduses.add(
                        con.createQuery(epicodusQuery)
                                .addParameter("epicodusId", epicodusId)
                                .executeAndFetchFirst(Epicodus.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return epicoduses;
    }

}
