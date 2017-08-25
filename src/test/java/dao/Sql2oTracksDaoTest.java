package dao;

import models.Tracks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oTracksDaoTest {

    private Sql2oTracksDao tracksDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        tracksDao = new Sql2oTracksDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingSetsId() throws Exception {
        Tracks tracks = setupNewTrack();
        int origTrackId = tracks.getId();
        tracksDao.add(tracks);
        assertNotEquals(origTrackId, tracks.getId());
    }

    @Test
    public void ExistingTrackCanBeFoundById() throws Exception {
        Tracks tracks = setupNewTrack();
        tracksDao.add(tracks);
        Tracks foundTrack = tracksDao.findById(tracks.getId());
        assertNotEquals(tracks, foundTrack);
    }

    @Test
    public void returnAlladdedTracksFromgetAll() throws Exception {
        Tracks tracks = setupNewTrack();
        tracksDao.add(tracks);
        assertEquals(1, tracksDao.getAll().size());
    }

    @Test
    public void noTracksReturnsEmptyList() throws Exception {
        assertEquals(0, tracksDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectReview() throws Exception {
        Tracks tracks = setupNewTrack();
        tracksDao.add(tracks);
        tracksDao.deleteTracksById(tracks.getId());
        assertEquals(0, tracksDao.getAll().size());
    }

    @Test
    public void IdIsReturnedCorrectly() throws Exception {
        Tracks tracks = setupNewTrack();
        int originalId = tracks.getEpicodusId();
        tracksDao.add(tracks);
        assertEquals(originalId, tracksDao.findById(tracks.getId()).getEpicodusId());
    }

    public Tracks setupNewTrack() {return new Tracks("test", 1, 1, "test");}
}