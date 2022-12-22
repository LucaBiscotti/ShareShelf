package com.example.shareshelf;

public class Users {

    // variables for storing our data.
    private String Name, Surname, Email, PhoneNumber, Password, Address, Id;
    private Integer Points;

    public Users(){
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Users(String Surname, String Email, String Address, String Name, String Password, Integer Points, String PhoneNumber) {
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Password = Password;
        this.Address = Address;
        this.Points = Points;
    }

    // Constructor for all variables.
    public Users(String Surname, String Email, String Address, String Name, Integer Points, String PhoneNumber) {
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
        this.Points = Points;
    }

    // getter methods for all variables.
    public String getId() { return Id; }

    public String getName() {
        return Name;
    }

    public Integer getPoints(){
        return Points;
    }

    public String getSurname(){
        return Surname;
    }

    public String getEmail(){
        return Email;
    }

    public String getPhoneNumber(){
        return PhoneNumber;
    }

    public String getPassword(){
        return Password;
    }

    public String getAddress(){
        return Address;
    }

    // setter method for all variables.
    public void setId(String Id) {this.Id = Id;}

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

    public void setPassword(String Password){
        this.Password = Password;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public void setPoints(Integer Points){
        this.Points = Points;
    }

}
