package com.instil.controllers;

import com.instil.logic.QuotesEngine;
import com.instil.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/quotes")
public class QuotesController {
    @Autowired
    private QuotesEngine engine;

    @GetMapping(value="{courseId}", produces = "application/json")
    public ResponseEntity<Mono<Quote>> fetchQuoteAsJson(@PathVariable String courseId) {
        return engine.generateQuote(courseId)
                .map(Mono::just)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }
}
