package com.instil.mvc.model;

public class Quote {
    private Course course;
    private double amount;

    public Quote(Course course, double amount) {
        this.course = course;
        this.amount = amount;
    }

    public Quote() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
