package ru.mai.javafx.javafxcalendarapplication;

public class Plan {
    private int id_user;
    private String note;

    public Plan(int id_user, String note) {
        this.setId_user(id_user);
        this.setNote(note);
    }

    public Plan() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
