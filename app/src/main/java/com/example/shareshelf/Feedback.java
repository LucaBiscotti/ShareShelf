package com.example.shareshelf;

public class Feedback {
    private String Text, IdOfferer, IdUser;
    private Integer Rate;

    public Feedback(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Feedback(String IdOfferer, String IdUser, String Text, Integer Rate){
        this.IdOfferer = IdOfferer;
        this.IdUser = IdUser;
        this.Text = Text;
        this.Rate = Rate;
    }

    // getter methods for all variables.
    public String getIdOfferer() {
        return IdOfferer;
    }

    public String getIdUser(){
        return IdUser;
    }

    public String getText(){
        return Text;
    }

    public Integer getRate(){
        return Rate;
    }

    // setter method for all variables.
    public void setIdOfferer(String IdOfferer) {
        this.IdOfferer = IdOfferer;
    }

    public void setIdUser(String IdUser){
        this.IdUser = IdUser;
    }

    public void setText(String Text){
        this.Text = Text;
    }

    public void setRate(Integer Rate){
        this.Rate = Rate;
    }

}
