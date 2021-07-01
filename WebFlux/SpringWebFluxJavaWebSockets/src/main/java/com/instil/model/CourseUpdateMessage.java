package com.instil.model;

public class CourseUpdateMessage {
    private CourseUpdateType type;
    private String courseId;

    public CourseUpdateMessage(CourseUpdateType type, String courseId) {
        this.type = type;
        this.courseId = courseId;
    }

    public CourseUpdateType getType() {
        return type;
    }

    public void setType(CourseUpdateType type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
