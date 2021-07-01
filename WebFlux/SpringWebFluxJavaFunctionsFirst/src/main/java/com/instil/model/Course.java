package com.instil.model;

public class Course {
    private String id;
    private String title;
    private CourseDifficulty difficulty;
    private int duration;

    public Course() {
    }

    public Course(String id, String title, CourseDifficulty difficulty, int duration) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CourseDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(CourseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
