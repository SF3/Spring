package com.instil.mvc;

import com.instil.mvc.logic.QuotesEngine;
import com.instil.mvc.model.Course;
import com.instil.mvc.model.Quote;
import com.instil.mvc.services.QuotesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuotesController.class)
public class QuotesControllerFirstIntegrationTest {
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuotesEngine engine;

    @BeforeEach
    public void start() {
        Quote tmpQuote = new Quote(new Course(), 5000.0);
        when(engine.generateQuote(anyString())).thenReturn(Optional.of(tmpQuote));
    }

    @Test
    public void quotesWork() throws Exception {
        mockMvc.perform(get("/quotes/AB12").accept(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.amount").value(5000.0));
    }
}
