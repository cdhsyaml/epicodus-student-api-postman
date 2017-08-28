package dao;

import models.Epicodus;
import models.ForeignStudent;
import models.Student;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oForeignStudentDao implements ForeignStudentDao {

    private final Sql2o sql2o;

    public Sql2oForeignStudentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(ForeignStudent foreignStudent) {
        String sql = "INSERT INTO foreignstudents (name, country) VALUES (:name, :country)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", foreignStudent.getName())
                    .addParameter("country", foreignStudent.getCountry())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("COUNTRY", "country")
                    .executeUpdate()
                    .getKey();
            foreignStudent.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public ForeignStudent findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM foreignstudents WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ForeignStudent.class);
        }
    }

    @Override
    public void update(int id, String newName, String newCountry) {
        String sql = "UPDATE foreignstudents SET (name, country) = (:name, :country) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", newName)
                    .addParameter("country", newCountry)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<ForeignStudent> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM foreignstudents")
                    .executeAndFetch(ForeignStudent.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from foreignstudents WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}
