package ru.mai.javafx.javafxcalendarapplication;

public class User {
    String firstName;
    String userName;
    String password;
    String checkPassword;

    public User(String firstName, String userName, String password, String checkPassword) {
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
        this.checkPassword = checkPassword;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
