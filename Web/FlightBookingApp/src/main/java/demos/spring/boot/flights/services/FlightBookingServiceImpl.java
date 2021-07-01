package demos.spring.boot.flights.services;

import demos.spring.boot.flights.domain.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightBookingServiceImpl implements FlightBookingService {
    public boolean makeBooking(Flight selected) {
        return Math.random() > 0.5;
    }
}
