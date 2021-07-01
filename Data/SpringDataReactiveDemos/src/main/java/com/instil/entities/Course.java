package com.instil.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("courses")
public class Course implements Persistable<String> {
    @Transient
    private boolean unsaved;
    private String type;
    private String number;
    private String title;

    public Course() {
        unsaved = true;
    }

    @Column("coursetype")
    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    @Id()
    @Column("coursenum")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column("coursetitle")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void markAsSaved() {
        unsaved = false;
    }

    @Override
    public String getId() {
        return number;
    }

    @Override
    public boolean isNew() {
        return unsaved;
    }
}

