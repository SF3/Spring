package com.instil.mvc.services;

import com.instil.mvc.logic.QuotesEngine;
import com.instil.mvc.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/quotes")
public class QuotesController {
    @Autowired
    private QuotesEngine engine;

    @GetMapping(value="{courseId}", produces = "application/json")
    public ResponseEntity<Quote> fetchQuoteAsJson(@PathVariable String courseId) {
        return engine.generateQuote(courseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }
}
