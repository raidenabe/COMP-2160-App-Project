package com.example.studentspecificproductivityapp;

public class SleepModel {
    private int id;
    private long sleepTime;
    private long wakeTime;
    private long duration;

    public SleepModel(int id, long sleepTime, long wakeTime, long duration){
        this.id = id;
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
        this.duration = duration;
    }

    public int getId(){
        return id;
    }

    public long getSleepTime(){
        return sleepTime;
    }
    public long getWakeTime(){
        return wakeTime;
    }
    public long getDuration(){
        return duration;
    }
}
