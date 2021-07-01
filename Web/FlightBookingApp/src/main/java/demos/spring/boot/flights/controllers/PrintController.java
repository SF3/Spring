package demos.spring.boot.flights.controllers;

import demos.spring.boot.flights.errors.NotImplementedYetException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/print")
public class PrintController {

    @RequestMapping(method = RequestMethod.GET)
    public String op1() {
        throw new NotImplementedYetException("Printing not supported yet!");
    }
}
