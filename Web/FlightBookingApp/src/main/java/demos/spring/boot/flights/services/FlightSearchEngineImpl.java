package demos.spring.boot.flights.services;

import demos.spring.boot.flights.domain.Flight;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FlightSearchEngineImpl implements FlightSearchEngine {

    public List<Flight> findFlights(String origin, String destination, Date departure) {
        final int BASE_TIME = 9;

        ArrayList<Flight> results = new ArrayList<Flight>();

        Calendar c = Calendar.getInstance(Locale.UK);
        c.setTime(departure);

        for (int i = 0; i < 10; i++) {
            c.set(Calendar.HOUR_OF_DAY, BASE_TIME + i);
            results.add(new Flight(1000 + i, origin, destination, c.getTime()));
        }
        return results;
    }
}
