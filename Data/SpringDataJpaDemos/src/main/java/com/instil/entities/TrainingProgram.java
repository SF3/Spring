
package com.instil.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "trainingprograms")
public class TrainingProgram implements Serializable {
    private Integer id;
    private String name;
    private Set<Delivery> deliveries;

    @OneToMany
    @JoinColumn(name = "trainingprogramnum")
    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Id
    @Column(name = "trainingprogramnum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
