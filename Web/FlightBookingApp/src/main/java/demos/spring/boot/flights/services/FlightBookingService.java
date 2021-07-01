
package demos.spring.boot.flights.services;

import demos.spring.boot.flights.domain.Flight;

public interface FlightBookingService {
    boolean makeBooking(Flight selected);
}