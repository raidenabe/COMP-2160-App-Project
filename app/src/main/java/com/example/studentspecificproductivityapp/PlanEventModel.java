package com.example.studentspecificproductivityapp;

public class PlanEventModel {
    private int userId;
    private String date, name, description, time;

    public PlanEventModel(int userId, String date, String name, String description, String time) {
        this.userId = userId;
        this.date = date;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
