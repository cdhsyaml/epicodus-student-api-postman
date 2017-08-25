package dao;


import models.Epicodus;
import models.Student;

import java.util.List;

public interface EpicodusDao {

    //create
    void add (Epicodus epicodus);

    void addEpicodusToStudent(Epicodus epicodus, Student student);

     //read
    List<Epicodus> getAll();

   List<Student> getAllStudentsForAEpicodus(int epicodusId);
//
    Epicodus findById(int id);
//
    //update epicodus info
    void update(int id, String name, String address, String zipcode, String phone, String email);
//
   //delete individual epicodus
    void deleteById(int id);
//
    //delete all epicodus
    void clearAllEpicodus();


}
