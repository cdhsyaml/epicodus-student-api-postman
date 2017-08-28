package dao;

import models.ForeignStudent;

import java.util.List;

public interface ForeignStudentDao {
    //create
    void add(ForeignStudent foreignStudent);

    //read
    //find individual student
    ForeignStudent findById(int id);

     List<ForeignStudent> getAll();

    //update
     void update(int id, String newName, String newCountry);

    // List<Epicodus> getAllEpicodusForAForeignStudent(int id);
    //
    //delete
    void deleteById(int id);
}
