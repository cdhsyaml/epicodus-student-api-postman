package models;

import java.sql.Timestamp;

public class Tracks {

    private String ratedBy;
    private int rating;
    private Timestamp createdAt;
    private int id;
    private int epicodusId;
    private String content;

    public Tracks(String ratedBy, int rating, int epicodusId, String content) {
        this.ratedBy = ratedBy;
        this.rating = rating;
        this.createdAt = createdAt;
        this.epicodusId = epicodusId;
        this.content = content;
    }

    public String getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(String ratedBy) {
        this.ratedBy = ratedBy;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpicodusId() {
        return epicodusId;
    }

    public void setEpicodusId(int epicodusId) {
        this.epicodusId = epicodusId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
