package exercise.finish.part2;

import org.springframework.stereotype.Component;

@Component("bookingService")
public class FlightBookingServiceMock implements FlightBookingService {
    @Override
    public boolean makeBooking(Flight selected) {
        return true;
    }
}
