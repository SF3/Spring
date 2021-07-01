package com.instil.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (duration != course.duration) return false;
        if (!Objects.equals(id, course.id)) return false;
        if (!Objects.equals(title, course.title)) return false;
        return difficulty == course.difficulty;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }
}
