import com.google.gson.Gson;
import dao.Sql2oForeignStudentDao;
import dao.Sql2oStudentDao;
import dao.Sql2oEpicodusDao;
import dao.Sql2oTracksDao;
import exceptions.ApiException;
import models.ForeignStudent;
import models.Student;
import models.Epicodus;
import models.Tracks;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

    Sql2oStudentDao studentDao;
    Sql2oForeignStudentDao foreignStudentDao;
    Sql2oEpicodusDao epicodusDao;
    Sql2oTracksDao tracksDao;
    Connection conn;
    Gson gson = new Gson();

    String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    epicodusDao=new Sql2oEpicodusDao(sql2o);
    studentDao=new Sql2oStudentDao(sql2o);
    tracksDao=new Sql2oTracksDao(sql2o);
    foreignStudentDao = new Sql2oForeignStudentDao(sql2o);

    conn=sql2o.open();

    //CREATE
        post("epicodus/new", "application/json", (req, res) -> {
            Epicodus epicodus = gson.fromJson(req.body(), Epicodus.class);
            epicodusDao.add(epicodus);
            res.status(201);;
            return gson.toJson(epicodus);
        });

        //READ
        get("/epicodus", "application/json", (req, res) -> {
            return gson.toJson(epicodusDao.getAll());
        });

        get("/epicodus/:id", "application/json", (req, res) -> {
            int epicodusId = Integer.parseInt(req.params("id"));
            Epicodus epicodus = epicodusDao.findById(epicodusId);

            if (epicodus == null) {
                throw new ApiException(String.format("No epicodus student with the id %d found", epicodusId), 404);
            }
            return gson.toJson(epicodus);
        });

        //UPDATE
        post("/epicodus/:id/update", "application/json", (req, res) -> {
            int epicodusId = Integer.parseInt(req.params("id"));
            Epicodus epicodus = gson.fromJson(req.body(), Epicodus.class);
            epicodusDao.update(epicodusId, epicodus.getName(), epicodus.getAddress(), epicodus.getZipcode(), epicodus.getPhone(), epicodus.getEmail(), epicodus.getLastJob(), epicodus.getAge());
            res.status(201);
            return gson.toJson(epicodus);
        });

        post("/epicodus/:id/students/:studentId", "application/json", (req, res) -> {
            int epicodusId = Integer.parseInt(req.params("id"));
            int studentId = Integer.parseInt(req.params("studentId"));
            Student student = studentDao.findById(studentId);
            Epicodus epicodus = epicodusDao.findById(epicodusId);
            epicodusDao.addEpicodusToStudent(epicodus, student);
            res.status(201);
            return gson.toJson(epicodusDao.getAllStudentsForAEpicodus(epicodusId));
        });

        get("/epicodus/:id/students", "application/json", (req, res) ->{
            int epicodusId = Integer.parseInt(req.params("id"));
            epicodusDao.getAllStudentsForAEpicodus(epicodusId);
            return gson.toJson(epicodusDao.getAllStudentsForAEpicodus(epicodusId));
        });

        //DELETE
        get("/epicodus/:id/delete", "application/json", (req, res) -> {
            int epicodusId = Integer.parseInt(req.params("id"));
            epicodusDao.deleteById(epicodusId);
            return epicodusId;
        });

        get("/epicodus/delete/all", "application/json", (req, res) -> {
            epicodusDao.clearAllEpicodus();
            return epicodusDao.getAll().size();
        });

        //Student
        post("/students/new", "application/json", (req, res) -> {
            Student student = gson.fromJson(req.body(), Student.class);
            studentDao.add(student);
            res.status(201);;
            return gson.toJson(student);
        });

        get("/students", "application/json", (req, res) -> {
            return gson.toJson(studentDao.getAll());
        });

        get("/students/:id", "application/json", (req, res) -> {
            int studentId = Integer.parseInt(req.params("id"));
            return gson.toJson(studentDao.findById(studentId));
        });

        get("/students/:id/epicodus", "application/json", (req, res) ->{
            int studentId = Integer.parseInt(req.params("id"));
            studentDao.getAllEpicodusForAStudent(studentId);
            return gson.toJson(studentDao.getAllEpicodusForAStudent(studentId));
        });

        get("/students/:id/delete", "application/json", (req,res)->{
            int studentId = Integer.parseInt(req.params("id"));
            studentDao.deleteById(studentId);
            return gson.toJson(studentId);
        });

        //TRACKS

        // Create
        post("/epicodus/:epicodusId/tracks/new", "application/json", (req, res) -> {
            int epicodusId = Integer.parseInt(req.params("epicodusId"));
            Tracks tracks = gson.fromJson(req.body(), Tracks.class);
            tracks.setEpicodusId(epicodusId);
            tracksDao.add(tracks);
            res.status(201);
            return gson.toJson(tracks);
        });

        // Delete
        get("/tracks/:id/delete", "application/json", (req,res)->{
            int trackId = Integer.parseInt(req.params("id"));
            tracksDao.deleteTracksById(trackId);
            return gson.toJson(trackId);
        });

        // Read
        get("/epicodus/:id/tracks", "application/json", (req,res)->{
            int epicodusId = Integer.parseInt(req.params("id"));
            tracksDao.getAllTracksByEpicodus(epicodusId);
            return gson.toJson(tracksDao.getAllTracksByEpicodus(epicodusId));
        });

        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
        exception(ApiException.class, (exc, req, res) -> {
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        //foreignstudents
        //CREATE
        post("foreignstudents/new", "application/json", (req, res) -> {
            ForeignStudent foreignStudent = gson.fromJson(req.body(), ForeignStudent.class);
            foreignStudentDao.add(foreignStudent);
            res.status(201);;
            return gson.toJson(foreignStudent);
        });

        //READ
        get("/foreignstudents", "application/json", (req, res) -> {
            return gson.toJson(foreignStudentDao.getAll());
        });

        get("/foreignstudents/:id", "application/json", (req, res) -> {
            int foreignstudentsId = Integer.parseInt(req.params("id"));
            ForeignStudent foreignStudent = foreignStudentDao.findById(foreignstudentsId);

            if (foreignStudent == null) {
                throw new ApiException(String.format("No epicodus student with the id %d found", foreignstudentsId), 404);
            }
            return gson.toJson(foreignStudent);
        });

        //UPDATE
        post("/foreignstudents/:id/update", "application/json", (req, res) -> {
            int foreignstudentsId = Integer.parseInt(req.params("id"));
            ForeignStudent foreignStudent = gson.fromJson(req.body(), ForeignStudent.class);
            foreignStudentDao.update(foreignstudentsId, foreignStudent.getName(), foreignStudent.getCountry());
            res.status(201);
            return gson.toJson(foreignStudent);
        });

        //DELETE
        get("/foreignstudents/:id/delete", "application/json", (req, res) -> {
            int foreignstudentsId = Integer.parseInt(req.params("id"));
            epicodusDao.deleteById(foreignstudentsId);
            return foreignstudentsId;
        });
    }
}
