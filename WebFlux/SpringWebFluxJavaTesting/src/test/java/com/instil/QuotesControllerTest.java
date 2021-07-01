package com.instil;

import com.instil.controllers.QuotesController;
import com.instil.logic.QuotesEngine;
import com.instil.model.Course;
import com.instil.model.Quote;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(QuotesController.class)
public class QuotesControllerTest {
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @MockBean
    private QuotesEngine engine;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    public void start() {
        Quote tmpQuote = new Quote(new Course(), 5000.0);
        when(engine.generateQuote(anyString())).thenReturn(Optional.of(tmpQuote));
    }

    @Test
    public void quotesWork() throws Exception {
        client.get()
            .uri("/quotes/AB12")
                .accept(JSON_CONTENT_TYPE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.amount")
                .value(Matchers.is(5000.0));
    }
}
