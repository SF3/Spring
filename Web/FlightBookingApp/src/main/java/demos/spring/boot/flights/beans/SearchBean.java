package demos.spring.boot.flights.beans;

import java.util.Date;

public class SearchBean {
    private String origin;
    private String destination;
    private int flightNumber;
    private String dateOfDeparture;
    private String dateOfReturn;
    private Date departDate;
    private Date returnDate;

    public SearchBean() {
        super();
    }

    public SearchBean(String origin, String destination, int flightNumber,
                      String dateOfDeparture, String dateOfReturn) {
        super();
        this.origin = origin;
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfReturn = dateOfReturn;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    //Not set from request - set during validation
    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
