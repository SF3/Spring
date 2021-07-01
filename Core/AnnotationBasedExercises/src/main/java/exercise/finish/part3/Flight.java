package exercise.finish.part3;

import java.util.Date;

public class Flight {
    private String origin;
    private String destination;
    private Date departure;

    public Flight(String origin, String destination, Date departure) {
        super();
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
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

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
