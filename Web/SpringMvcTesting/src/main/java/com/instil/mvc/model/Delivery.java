package com.instil.mvc.model;

import java.time.LocalDate;

public class Delivery {
    private String id;
    private Course course;
    private LocalDate startDate;
    private LocalDate endDate;

    public Delivery(String id, Course course, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Delivery() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
