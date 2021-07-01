package com.instil.logic;

import com.instil.model.Quote;

import java.util.Optional;

public interface QuotesEngine {
    Optional<Quote> generateQuote(String courseId);
}
