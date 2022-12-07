package com.example.shareshelf;

public class Booking {
    private String IdNoticeboard, IdCandidate;

    public Booking(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Booking(String IdNoticeboard, String IdCandidate){
        this.IdNoticeboard = IdNoticeboard;
        this.IdCandidate = IdCandidate;
    }

    // getter methods for all variables.
    public String getIdNoticeboard() {
        return IdNoticeboard;
    }

    public String getIdCandidate() {
        return IdCandidate;
    }

    // setter method for all variables.
    public void setIdNoticeboard(String IdNoticeboard) {
        this.IdNoticeboard = IdNoticeboard;
    }

    public void setIdCandidate(String IdCandidate){
        this.IdCandidate = IdCandidate;
    }
}
