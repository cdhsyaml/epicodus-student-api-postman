package dao;

import models.Tracks;

import javax.sound.midi.Track;
import java.util.List;

public interface TracksDao {

    //create
    void add(Tracks tracks);

    //read
    Tracks findById(int id);
    List<Tracks> getAllTracksByEpicodus(int epicodusId);

    List<Tracks> getAll();

    //delete
    void deleteTracksById(int id);

}
