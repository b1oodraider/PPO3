package ru.mai.javafx.javafxcalendarapplication.modules;

public class Plan {
    private int id_user;
    private String date;
    private String note;

    public Plan(int id_user, String date, String note) {
        this.id_user = id_user;
        this.date = date;
        this.note = note;
    }

    public Plan() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
