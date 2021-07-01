package exercise.finish.part4;

import java.util.*;

public class FlightSearchEngineMock implements FlightSearchEngine {
    @Override
    public List<Flight> findFlights(String origin, String destination, Date departure) {
        ArrayList<Flight> results = new ArrayList<Flight>();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(departure);
        calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.HOUR, 2);

        for (int i = 0; i < 5; i++) {
            calendar.roll(Calendar.HOUR, 1);
            results.add(new Flight(origin, destination, calendar.getTime()));
        }
        return results;
    }
}
