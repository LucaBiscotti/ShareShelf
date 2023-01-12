package com.example.shareshelf;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Noticeboard {

    private String Categoria, Descrizione, Tipo, Titolo, Id, Stato;
    private Long Durata;
    private DocumentReference IDCreatore;
    private Date DataInizio;
    public Noticeboard(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Noticeboard(/*String Id,*/ String Category, Date DateStart, String Description,
                                      Long Duration, DocumentReference IDCreator, String Type,
                                      String Title, String Stato){
        //this.Id = Id;
        this.Categoria = Category;
        this.DataInizio = DateStart;
        this.Descrizione = Description;
        this.Durata = Duration;
        this.IDCreatore = IDCreator;
        this.Tipo = Type;
        this.Titolo = Title;
        this.Stato = Stato;
    }

    // getter methods for all variables.
    public String getId() {return Id;}

    public String getCategoria() {
        return Categoria;
    }

    public Date getDataInizio(){
        return DataInizio;
    }

    public String getDescrizione(){
        return Descrizione;
    }

    public Long getDurata(){
        return Durata;
    }

    public DocumentReference getIDCreatore(){
        return IDCreatore;
    }

    public String getTipo(){
        return Tipo;
    }

    public String getTitolo(){
        return Titolo;
    }

    // setter method for all variables.
    public void setId(String Id) {this.Id = Id;}

    public void setCategoria(String Category) {
        this.Categoria = Category;
    }

    public void setDataInizio(Date DateStart){
        this.DataInizio = DateStart;
    }

    public void setDescrizione(String Description){
        this.Descrizione = Description;
    }

    public void setDurata(Long Duration){
        this.Durata = Duration;
    }

    public void setIDCreatore(DocumentReference IDCreatore){
        this.IDCreatore = IDCreatore;
    }

    public void setTipo(String Type){
        this.Tipo = Type;
    }

    public void setTitolo(String Title){
        this.Titolo = Title;
    }

    public String getStato() {
        return Stato;
    }

    public void setStato(String stato) {
        Stato = stato;
    }
}
