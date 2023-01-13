package com.example.shareshelf;

public class Users {

    // variables for storing our data.
    private String Name, Surname, Email, PhoneNumber, Address, Cap;
    private Integer Points;

    public Users(){
        // empty constructor
        // required for Firebase.
    }

    public Users(String Surname, String Email, String Name, String PhoneNumber, Integer points, String Address, String Cap)
    {
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Points = points;
        this.Address = Address;
        this.Cap = Cap;
    }

    // getter methods for all variables.
    public String getName() {
        return this.Name;
    }

    public Integer getPoints(){
        return this.Points;
    }

    public String getSurname(){
        return this.Surname;
    }

    public String getEmail(){
        return this.Email;
    }

    public String getPhoneNumber(){
        return this.PhoneNumber;
    }

    public String getAddress(){
        return this.Address;
    }

    public String getCap(){return this.Cap;}

    // setter method for all variables.
    public void setName(String Name) {
        this.Name = Name;
    }

    public void setSurname(String Surname){
        this.Surname = Surname;
    }

    public void setEmail(String Email){
        this.Email = Email;
    }

    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public void setPoints(Integer Points){
        this.Points = Points;
    }

    public void setCap(String cap) {
        this.Cap = cap;
    }
}
