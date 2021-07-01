package com.instil.mvc.services;

import com.instil.mvc.model.Delivery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveriesController {
    @GetMapping(produces = "application/json")
    public List<Delivery> viewAllAsJson() {
        return new LinkedList<>();
    }
}
