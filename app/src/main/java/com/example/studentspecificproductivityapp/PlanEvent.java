package com.example.studentspecificproductivityapp;

import java.time.LocalDate;

public class PlanEvent {
    private String name;
    private LocalDate date;
    private LocalDate time;

    public PlanEvent(String name, LocalDate date, LocalDate time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
