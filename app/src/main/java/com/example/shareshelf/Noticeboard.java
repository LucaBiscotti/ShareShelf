package com.example.shareshelf;

import java.util.Date;

public class Noticeboard {

    private String Category, Description, IDCreator, Type, Title;
    private Date DateStart;
    private Integer Duration;

    public Noticeboard(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Noticeboard(String Category, Date DateStart, String Description, Integer Duration, String IDCreator, String Type, String Title){
        this.Category = Category;
        this.DateStart = DateStart;
        this.Description = Description;
        this.Duration = Duration;
        this.IDCreator = IDCreator;
        this.Type = Type;
        this.Title = Title;
    }

    // getter methods for all variables.
    public String getCategory() {
        return Category;
    }

    public Date getDateStart(){
        return DateStart;
    }

    public String getDescription(){
        return Description;
    }

    public Integer getDuration(){
        return Duration;
    }

    public String getIDCreator(){
        return IDCreator;
    }

    public String getType(){
        return Type;
    }

    public String getTitle(){
        return Title;
    }

    // setter method for all variables.
    public void setCategory(String Category) {
        this.Category = Category;
    }

    public void setDateStart(Date DateStart){
        this.DateStart = DateStart;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    public void setDuration(Integer Duration){
        this.Duration = Duration;
    }

    public void setIDCreator(String IDCreator){
        this.IDCreator = IDCreator;
    }

    public void setType(String Type){
        this.Type = Type;
    }

    public void setTitle(String Title){
        this.Title = Title;
    }
}
