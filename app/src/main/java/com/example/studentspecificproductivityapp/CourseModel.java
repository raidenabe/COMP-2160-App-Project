package com.example.studentspecificproductivityapp;

public class CourseModel {
    private int userId;
    private String courseCode, courseName, courseDaysOfTheWeek, courseHours;

    public CourseModel(int userId, String courseCode, String courseName, String courseDaysOfTheWeek, String courseHours) {
        this.userId = userId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDaysOfTheWeek = courseDaysOfTheWeek;
        this.courseHours = courseHours;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDaysOfTheWeek() {
        return courseDaysOfTheWeek;
    }

    public void setCourseDaysOfTheWeek(String courseDaysOfTheWeek) {
        this.courseDaysOfTheWeek = courseDaysOfTheWeek;
    }

    public String getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(String courseHours) {
        this.courseHours = courseHours;
    }

}
