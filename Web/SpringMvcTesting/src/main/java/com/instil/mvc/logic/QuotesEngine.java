package com.instil.mvc.logic;

import com.instil.mvc.model.Quote;

import java.util.Optional;

public interface QuotesEngine {
    Optional<Quote> generateQuote(String courseId);
}
