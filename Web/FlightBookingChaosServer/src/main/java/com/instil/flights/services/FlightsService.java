package com.instil.flights.services;

import com.instil.flights.model.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/flights")
public class FlightsService {
    private List<Flight> schedule;

    @Resource(name = "schedule")
    public void setSchedule(List<Flight> schedule) {
        this.schedule = schedule;
    }

    @GetMapping(produces = "application/json")
    public List<Flight> allFlights() {
        return schedule;
    }

    @GetMapping(value = "/{number}", produces = "application/json")
    public ResponseEntity<Flight> flightByNumber(@PathVariable("number") int number) {
        return schedule.stream()
                .filter(f -> f.getNumber() == number)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/origin/{origin}", produces = "application/json")
    public List<Flight> flightsByOrigin(@PathVariable("origin") String origin) {
        List<Flight> results = new ArrayList<Flight>();
        for (Flight f : schedule) {
            if (f.getOrigin().equals(origin)) {
                results.add(f);
            }
        }
        return results;
    }

    @GetMapping(value = "/destination/{destination}", produces = "application/json")
    public List<Flight> flightsByDestination(@PathVariable("destination") String destination) {
        List<Flight> results = new ArrayList<Flight>();
        for (Flight f : schedule) {
            if (f.getDestination().equals(destination)) {
                results.add(f);
            }
        }
        return results;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Flight> deleteFlight(@PathVariable("id") int id) {
        Flight found;
        if ((found = removeFlight(id)) != null) {
            return new ResponseEntity<Flight>(found, HttpStatus.OK);
        }
        return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}",
            produces = "application/json",
            consumes = "application/json")
    public int updateFlight(@PathVariable("id") int id, @RequestBody Flight flight) {
        removeFlight(id);
        schedule.add(flight);
        return flight.getNumber();
    }

    private Flight removeFlight(int id) {
        Iterator<Flight> iter = schedule.iterator();
        while (iter.hasNext()) {
            Flight f = iter.next();
            if (f.getNumber() == id) {
                iter.remove();
                return f;
            }
        }
        return null;
    }
}
