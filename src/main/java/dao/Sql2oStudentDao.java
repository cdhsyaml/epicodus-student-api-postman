package dao;

import models.Student;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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



}
