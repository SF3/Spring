package com.instil.flights;

import com.instil.flights.model.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FlightBookingConfiguration {
    @Bean(name = "schedule")
    public List<Flight> buildSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(new Flight(101, "Belfast City", "London Gatwick", today, tomorrow));
        flights.add(new Flight(102, "Belfast City", "London Gatwick", today, tomorrow));
        flights.add(new Flight(103, "Belfast City", "London Gatwick", today, tomorrow));
        flights.add(new Flight(104, "Belfast City", "London Gatwick", today, tomorrow));
        flights.add(new Flight(105, "Belfast City", "London Gatwick", today, tomorrow));
        flights.add(new Flight(201, "Belfast City", "Birmingham", today, tomorrow));
        flights.add(new Flight(202, "Belfast City", "Birmingham", today, tomorrow));
        flights.add(new Flight(203, "Belfast City", "Birmingham", today, tomorrow));
        flights.add(new Flight(204, "Belfast City", "Birmingham", today, tomorrow));
        flights.add(new Flight(205, "Belfast City", "Birmingham", today, tomorrow));
        flights.add(new Flight(301, "Dublin", "Edinburgh", today, tomorrow));
        flights.add(new Flight(302, "Dublin", "Edinburgh", today, tomorrow));
        flights.add(new Flight(303, "Dublin", "Edinburgh", today, tomorrow));
        flights.add(new Flight(304, "Dublin", "Edinburgh", today, tomorrow));
        flights.add(new Flight(305, "Dublin", "Edinburgh", today, tomorrow));
        flights.add(new Flight(401, "Dublin", "Manchester", today, tomorrow));
        flights.add(new Flight(402, "Dublin", "Manchester", today, tomorrow));
        flights.add(new Flight(403, "Dublin", "Manchester", today, tomorrow));
        flights.add(new Flight(404, "Dublin", "Manchester", today, tomorrow));
        flights.add(new Flight(405, "Dublin", "Manchester", today, tomorrow));
        return flights;
    }
}
