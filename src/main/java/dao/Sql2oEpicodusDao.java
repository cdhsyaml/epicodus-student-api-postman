package dao;

import models.Epicodus;
import models.Student;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oEpicodusDao implements EpicodusDao {

    private final Sql2o sql2o;

    public Sql2oEpicodusDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Epicodus epicodus) {
        String sql = "INSERT INTO epicodus (name, address, zipcode, phone, email) VALUES (:name, :address, :zipcode, :phone, :email)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", epicodus.getName())
                    .addParameter("address", epicodus.getAddress())
                    .addParameter("zipcode", epicodus.getZipcode())
                    .addParameter("phone", epicodus.getPhone())
                    .addParameter("email", epicodus.getEmail())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("ADDRESS", "address")
                    .addColumnMapping("ZIPCODE", "zipcode")
                    .addColumnMapping("PHONE", "phone")
                    .addColumnMapping("EMAIL", "email")
                    .executeUpdate()
                    .getKey();
            epicodus.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Epicodus findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM epicodus WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Epicodus.class);
        }
    }

    @Override
    public List<Epicodus> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM epicodus")
                    .executeAndFetch(Epicodus.class);
        }
    }

    @Override
    public void update(int id, String newName, String newAddress, String newZip, String newPhone, String newEmail) {
        String sql = "UPDATE epicodus SET (name, address, zipcode, phone, email) = (:name, :address, :zipcode, :phone, :email) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", newName)
                    .addParameter("address", newAddress)
                    .addParameter("zipcode", newZip)
                    .addParameter("phone", newPhone)
                    .addParameter("email", newEmail)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from epicodus WHERE id = :id";
        String deleteJoin = "DELETE from epicodus_students WHERE epicodusId = :epicodusId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("epicodusId", id)
                    .executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllEpicodus() {
        String sql = "DELETE from epicodus";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addEpicodusToStudent(Epicodus epicodus, Student student) {
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
    public List<Student> getAllStudentsForAEpicodus(int epicodusId) {
        List<Student> students = new ArrayList();
        String joinQuery = "SELECT studentId FROM epicodus_students WHERE epicodusId = :epicodusId";

        try (Connection con = sql2o.open()) {
            List<Integer> allStudentIds = con.createQuery(joinQuery)
                    .addParameter("epicodusId", epicodusId)
                    .executeAndFetch(Integer.class);
            for (Integer individualStudentId : allStudentIds) {
                String foodtypeQuery = "SELECT * FROM students WHERE id = :studentId";
                students.add(
                        con.createQuery(foodtypeQuery)
                                .addParameter("studentId", individualStudentId)
                                .executeAndFetchFirst(Student.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return students;
    }



}
