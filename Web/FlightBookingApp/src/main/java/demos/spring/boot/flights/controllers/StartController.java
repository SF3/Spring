package demos.spring.boot.flights.controllers;

import demos.spring.boot.flights.beans.SearchBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/start")
public class StartController {
    @ModelAttribute("searchBean")
    public SearchBean op1() {
        return new SearchBean("LON", "JFK", 0, "2018-02-11", "2018-02-17");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String op2(@ModelAttribute("searchBean") SearchBean details, Map<String, Object> model) {
        model.put("data", details);
        return "flightStart";
    }
}
