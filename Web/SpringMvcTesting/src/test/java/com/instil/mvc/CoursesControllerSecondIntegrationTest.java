package com.instil.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CoursesControllerSecondIntegrationTest {

    private static final MediaType JSON_CONTENT_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allCoursesCanBeFound() throws Exception {
        mockMvc.perform(get("/courses").accept(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[?(@.difficulty=='BEGINNER')]", hasSize(4)))
                .andExpect(jsonPath("$[?(@.difficulty=='INTERMEDIATE')]", hasSize(4)))
                .andExpect(jsonPath("$[?(@.difficulty=='ADVANCED')]", hasSize(4)));
    }

    @Test
    public void coursesCanBeFoundByID() throws Exception {
        mockMvc.perform(get("/courses/AB12").accept(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id").value("AB12"))
                .andExpect(jsonPath("$.title").value("Intro to Scala"))
                .andExpect(jsonPath("$.difficulty").value("BEGINNER"))
                .andExpect(jsonPath("$.duration").value(4));
    }

    @Test
    @DirtiesContext
    public void coursesCanBeRemoved() throws Exception {
        mockMvc.perform(delete("/courses/AB12"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/courses").accept(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$[?(@.title=='Intro to Scala')]", hasSize(0)));
    }

    @Test
    @DirtiesContext
    public void coursesCanBeAdded() throws Exception {
        String content = "{\"id\":\"YZ98\",\"title\":\"Extra Hard Haskell\",\"difficulty\":\"ADVANCED\",\"duration\":5}";
        mockMvc.perform(put("/courses/YZ98").contentType(JSON_CONTENT_TYPE).content(content))
                .andExpect(status().isOk());
        mockMvc.perform(get("/courses").accept(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(13)))
                .andExpect(jsonPath("$[?(@.title=='Extra Hard Haskell')]", hasSize(1)));
        mockMvc.perform(get("/courses/YZ98").accept(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id").value("YZ98"))
                .andExpect(jsonPath("$.title").value("Extra Hard Haskell"))
                .andExpect(jsonPath("$.difficulty").value("ADVANCED"))
                .andExpect(jsonPath("$.duration").value(5));
    }


}
