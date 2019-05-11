package com.example.eventbuddy;

public class Event {
    private String location;
    private String date;
    private String listAct;
    private String description;

    public Event() {

    }

    public Event(String location, String date, String listAct, String description) {
        this.location = location;
        this.date = date;
        this.listAct = listAct;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListAct() {
        return listAct;
    }

    public void setListAct(String listAct) {
        this.listAct = listAct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
