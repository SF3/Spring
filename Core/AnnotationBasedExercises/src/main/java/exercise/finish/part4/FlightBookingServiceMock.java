package exercise.finish.part4;

public class FlightBookingServiceMock implements FlightBookingService {
    @Override
    public boolean makeBooking(Flight selected) {
        return true;
    }
}
