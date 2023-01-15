package com.example.shareshelf;

import com.google.firebase.firestore.DocumentReference;

public class Booking {
    private DocumentReference IdAnnuncio, IdCandidato;

    public Booking(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Booking(DocumentReference IdNoticeboard, DocumentReference IdCandidate){
        this.IdAnnuncio = IdNoticeboard;
        this.IdCandidato = IdCandidate;
    }

    // getter methods for all variables.

    public DocumentReference getIdNoticeboard() {
        return IdAnnuncio;
    }

    public DocumentReference getIdCandidate() {
        return IdCandidato;
    }

    // setter method for all variables.
    public void setIdNoticeboard(DocumentReference IdNoticeboard) {
        this.IdAnnuncio = IdNoticeboard;
    }

    public void setIdCandidate(DocumentReference IdCandidate){
        this.IdCandidato = IdCandidate;
    }
}
