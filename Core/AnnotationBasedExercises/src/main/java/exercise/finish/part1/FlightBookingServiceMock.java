package exercise.finish.part1;

import org.springframework.stereotype.Component;

@Component
public class FlightBookingServiceMock implements FlightBookingService {
    @Override
    public boolean makeBooking(Flight selected) {
        return true;
    }
}
