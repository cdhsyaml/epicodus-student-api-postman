package dao;

import models.Tracks;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTracksDao implements TracksDao {

    private final Sql2o sql2o;

    public Sql2oTracksDao(Sql2o sql2o) {this.sql2o = sql2o;}

    @Override
    public void add(Tracks track) {
        String sql = "INSERT INTO tracks (ratedBy, rating, epicodusId, content) VALUES (:ratedBy, :rating, :epicodusId, :content)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("ratedBy", track.getRatedBy())
                    .addParameter("rating", track.getRating())
                    .addParameter("epicodusId", track.getEpicodusId())
                    .addParameter("content", track.getContent())
                    .addColumnMapping("RATEDBY", "ratedBy")
                    .addColumnMapping("RATING", "rating")
                    .addColumnMapping("EPICODUSID", "epicodusId")
                    .addColumnMapping("CONTENT", "content")
                    .executeUpdate()
                    .getKey();
            track.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Tracks findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tracks WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Tracks.class);
        }
    }

    @Override
    public List<Tracks> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tracks")
                    .executeAndFetch(Tracks.class);
        }
    }

    @Override
    public void deleteTracksById(int id) {
        String sql = "DELETE from tracks WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public List<Tracks>getAllTracksByEpicodus(int epicodusId) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM tracks WHERE epicodusId = :epicodusId")
                    .addParameter("epicodusId", epicodusId)
                    .executeAndFetch(Tracks.class);
        }
    }
}
