package exercise.start;

import java.util.Date;
import java.util.List;

public class BookingController {
    private FlightSearchEngine searchEngine;
    private FlightBookingService bookingService;

    public BookingController() {
        searchEngine = new FlightSearchEngine();
        bookingService = new FlightBookingService();
    }

    public List<Flight> findAvailableFlights(String origin, String destination, Date departure) {
        return searchEngine.findFlights(origin, destination, departure);
    }

    public boolean bookFlight(Flight selected) {
        return bookingService.makeBooking(selected);
    }
}