package com.instil.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuotesControllerSecondIntegrationTest {

	private static final MediaType JSON_CONTENT_TYPE = MediaType.parseMediaType("application/json;charset=UTF-8");

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void canQuoteForBeginnerCourses() throws Exception {
		mockMvc.perform(get("/quotes/AB12").accept(JSON_CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.course.id").value( "AB12"))
				.andExpect(jsonPath("$.amount").value(4800.0));

	}

	@Test
	public void canQuoteForIntermediateCourses() throws Exception {
		mockMvc.perform(get("/quotes/CD34").accept(JSON_CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.course.id").value( "CD34"))
				.andExpect(jsonPath("$.amount").value(4200.0));

	}

	@Test
	public void canQuoteForAdvancedCourses() throws Exception {
		mockMvc.perform(get("/quotes/EF56").accept(JSON_CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.course.id").value( "EF56"))
				.andExpect(jsonPath("$.amount").value(3200.0));

	}
}
