package demos.spring.boot.flights.domain;

import java.io.Serializable;
import java.util.Date;

public class Flight implements Serializable {
    private int number;
    private String origin;
    private String destination;
    private Date departure;

    public Flight(int number, String origin, String destination, Date departure) {
        super();
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }
}
