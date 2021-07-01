package demos.spring.boot.flights.controllers;

import demos.spring.boot.flights.domain.Flight;
import demos.spring.boot.flights.services.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book/{number}")
@SessionAttributes({"flightsOut", "flightsBack", "chosenOut", "chosenBack"})
public class BookingController {
    private FlightBookingService flightBookingService;

    @Autowired
    public BookingController(FlightBookingService flightBookingService) {
        super();
        this.flightBookingService = flightBookingService;
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    protected String op1(@PathVariable("number") String number,
                         @ModelAttribute("flightsOut") List<Flight> flightsOut,
                         Map<String, Object> model) {

        int numberOut = Integer.parseInt(number);

        Flight chosenOut = null;
        for (Flight f : flightsOut) {
            if (f.getNumber() == numberOut) {
                chosenOut = f;
            }
        }

        model.put("chosenOut", chosenOut);
        return "returningFlightSelection";
    }

    @RequestMapping(value = "/back", method = RequestMethod.GET)
    protected String op2(@PathVariable("number") String number,
                         @ModelAttribute("chosenOut") Flight chosenOut,
                         @ModelAttribute("flightsBack") List<Flight> flightsBack,
                         Map<String, Object> model) {

        int numberBack = Integer.parseInt(number);

        Flight chosenBack = null;
        for (Flight f : flightsBack) {
            if (f.getNumber() == numberBack) {
                chosenBack = f;
            }
        }

        model.put("chosenBack", chosenBack);

        if (flightBookingService.makeBooking(chosenOut)) {
            if (flightBookingService.makeBooking(chosenBack)) {
                return "bookingSuccess";
            }
        }
        return "bookingFailure";
    }
} 