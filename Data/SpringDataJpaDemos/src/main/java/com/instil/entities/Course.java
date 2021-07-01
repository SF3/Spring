package com.instil.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "courses")
public class Course implements Serializable {
    private List<Delivery> deliveries;
    private ComputerSetup setup;
    private String type;
    private String number;
    private String title;

    @Column(name = "coursetype")
    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    @Id()
    @Column(name = "coursenum")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "coursetitle")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Embedded
    public ComputerSetup getSetup() {
        return setup;
    }

    public void setSetup(ComputerSetup setup) {
        this.setup = setup;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn(name = "deliverynum")
    @JoinColumn(name = "coursenum")
    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}

