package com.example.studentspecificproductivityapp;

public class TaskModel {
    private int id;
    private int userId;
    private String title;
    private String desc;
    private boolean completed;

    public TaskModel(int id, int userId, String title, String desc, boolean completed){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.completed = completed;
    }
    public int getId(){return id;}
    public int getUserId(){return userId;}
    public String getTitle(){return title;}
    public String getDesc(){return  desc;}
    public boolean isCompleted(){return completed;}

    public void setCompleted(boolean completed){this.completed=completed;}

}
