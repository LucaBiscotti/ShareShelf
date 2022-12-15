package com.example.shareshelf;

public class Booking {
    private String IdNoticeboard, IdCandidate, Id;

    public Booking(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Booking(String Id, String IdNoticeboard, String IdCandidate){
        this.Id = Id;
        this.IdNoticeboard = IdNoticeboard;
        this.IdCandidate = IdCandidate;
    }

    // getter methods for all variables.
    public String getId(){ return Id;}

    public String getIdNoticeboard() {
        return IdNoticeboard;
    }

    public String getIdCandidate() {
        return IdCandidate;
    }

    // setter method for all variables.
    public void setId(String Id){ this.Id = Id; }

    public void setIdNoticeboard(String IdNoticeboard) {
        this.IdNoticeboard = IdNoticeboard;
    }

    public void setIdCandidate(String IdCandidate){
        this.IdCandidate = IdCandidate;
    }
}
