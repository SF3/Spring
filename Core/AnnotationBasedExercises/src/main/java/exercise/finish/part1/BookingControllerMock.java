package exercise.finish.part1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BookingControllerMock implements BookingController {
    @Override
    public List<Flight> findAvailableFlights(String origin, String destination, Date departure) {
        return searchEngine.findFlights(origin, destination, departure);
    }

    @Override
    public boolean bookFlight(Flight selected) {
        return bookingService.makeBooking(selected);
    }

    @Autowired
    public void setSearchEngine(FlightSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Autowired
    public void setBookingService(FlightBookingService bookingService) {
        this.bookingService = bookingService;
    }

    private FlightSearchEngine searchEngine;
    private FlightBookingService bookingService;
}