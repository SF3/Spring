package exercise.finish.part4;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class BookingControllerMock implements BookingController {
    private FlightSearchEngine searchEngine;
    private FlightBookingService bookingService;

    @Override
    public List<Flight> findAvailableFlights(String origin, String destination, Date departure) {
        return searchEngine.findFlights(origin, destination, departure);
    }

    @Override
    public boolean bookFlight(Flight selected) {
        return bookingService.makeBooking(selected);
    }

    @Resource(name = "searchEngine")
    public void setSearchEngine(FlightSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Resource(name = "bookingService")
    public void setBookingService(FlightBookingService bookingService) {
        this.bookingService = bookingService;
    }
}