package demos.spring.boot.flights.controllers;

import demos.spring.boot.flights.beans.SearchBean;
import demos.spring.boot.flights.domain.Flight;
import demos.spring.boot.flights.services.FlightSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
@SessionAttributes({"flightsOut", "flightsBack"})
public class SearchController {
    private FlightSearchEngine flightSearchEngine;
    private Validator validator;

    @Autowired
    public SearchController(FlightSearchEngine flightSearchEngine,
                            @Qualifier("flightSearchValidator") Validator validator) {
        super();
        this.flightSearchEngine = flightSearchEngine;
        this.validator = validator;
    }

    @InitBinder("searchBean")
    public void binding(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String op1(@Valid SearchBean bean, BindingResult errors, Map<String, Object> model) throws Exception {

        if (errors.hasErrors()) {
            model.put("flightsErrors", errors);
            model.put("data", bean);

            return "flightStart";
        } else {
            List<Flight> flightsOut = flightSearchEngine.findFlights(bean.getOrigin(),
                    bean.getDestination(),
                    bean.getDepartDate());
            List<Flight> flightsBack = flightSearchEngine.findFlights(bean.getDestination(),
                    bean.getOrigin(),
                    bean.getReturnDate());

            model.put("flightsOut", flightsOut);
            model.put("flightsBack", flightsBack);

            return "outwardsFlightSelection";
        }
    }
}
