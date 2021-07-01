package demos.spring.boot.flights.services;

import demos.spring.boot.flights.domain.Flight;

import java.util.Date;
import java.util.List;

public interface FlightSearchEngine {
    List<Flight> findFlights(String origin, String destination, Date departure);
}