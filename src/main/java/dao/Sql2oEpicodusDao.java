package dao;

import models.Epicodus;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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


}
