package com.example.studentspecificproductivityapp;

public class StudySessionModel {
    private int id;
    private int userId;
    private String subject;
    private long startTime;
    private long endTime;
    private long duration;
    private String notes;

    public StudySessionModel(int id, int userId, String subject, long startTime, long endTime, long duration, String notes) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getSubject() {
        return subject;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public String getNotes() {
        return notes;
    }
}
