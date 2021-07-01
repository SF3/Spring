package demos.spring.boot.flights.beans;

public class BookingBean {
    private int flightNumberOut;
    private int flightNumberBack;

    public BookingBean() {
        super();
    }

    public int getFlightNumberBack() {
        return flightNumberBack;
    }

    public void setFlightNumberBack(int flightNumberBack) {
        this.flightNumberBack = flightNumberBack;
    }

    public int getFlightNumberOut() {
        return flightNumberOut;
    }

    public void setFlightNumberOut(int flightNumberOut) {
        this.flightNumberOut = flightNumberOut;
    }
}
