package com.instil.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@SuppressWarnings("serial")
@Embeddable
public class ComputerSetup implements Serializable {
    private String hardware;
    private String OS;
    private String software;

    @Column(name = "machinespec")
    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    @Column(name = "operatingsystemspec")
    public String getOS() {
        return OS;
    }

    public void setOS(String os) {
        OS = os;
    }

    @Column(name = "softwarespec")
    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }
}

