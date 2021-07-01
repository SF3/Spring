package exercise.finish.part3;

public class FlightBookingServiceMock implements FlightBookingService {
    @Override
    public boolean makeBooking(Flight selected) {
        return true;
    }
}
