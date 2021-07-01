package exercise.finish.part3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utilities {
    public static List<Flight> getSpecialOffers() {
        List<Flight> results = new ArrayList<Flight>();
        results.add(new Flight("Dublin", "New York", new Date()));
        results.add(new Flight("Dublin", "Paris", new Date()));
        results.add(new Flight("Dublin", "Barcelona", new Date()));
        return results;
    }

    public static List<String> getAirports() {
        List<String> results = new ArrayList<String>();
        results.add("Heathrow");
        results.add("Belfast International");
        results.add("Belfast City");
        results.add("Dublin");
        results.add("Birmingham");
        results.add("Luton");
        return results;
    }

    public static String[] getMonths() {
        return new String[]{"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October",
                "November", "December"};
    }

    public static Integer[] getDays() {
        Integer[] days = new Integer[31];
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }
        return days;
    }
}
