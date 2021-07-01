package com.instil.mvc;

import com.instil.mvc.services.DeliveriesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DeliveriesController.class)
public class DeliveriesControllerIntegrationTest {
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allDeliveriesCanBeFound() throws Exception {
        mockMvc.perform(get("/deliveries").accept(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
