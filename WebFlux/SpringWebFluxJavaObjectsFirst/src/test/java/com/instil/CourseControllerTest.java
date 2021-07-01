package com.instil;

import com.instil.model.Course;
import com.instil.model.CourseDifficulty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/*
    Duplication not refactored for demo purposes.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseControllerTest {
	@Autowired
	private ApplicationContext context;
	private WebTestClient client;

	@Before
	public void start() {
		client = WebTestClient
				.bindToApplicationContext(context)
				.configureClient()
				.responseTimeout(Duration.ofSeconds(20))
				.build();
	}

	@Test
	public void canFindAllCourses() {
		client.get()
				.uri("/courses")
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Course.class)
                .hasSize(12);

	}

	@Test
	public void canFindAllCoursesViaFlux() {
		FluxExchangeResult<Course> result = client.get()
				.uri("/courses")
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Course.class);

		StepVerifier.create(result.getResponseBody())
				.expectNextMatches(c -> c.getId().equals("AB12"))
                .expectNextMatches(c -> c.getId().equals("CD34"))
                .expectNextMatches(c -> c.getId().equals("EF56"))
                .expectNextCount(9)
				.expectComplete()
				.verify();
	}

	@Test
	public void canFindIndividualCourses() {
		String id = "AB12";
		Course result = client.get()
				.uri("/courses/" + id)
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Course.class)
                .returnResult()
				.getResponseBody();

		assertEquals("Programming in Scala", result.getTitle());
		assertSame(CourseDifficulty.BEGINNER, result.getDifficulty());
		assertEquals(4, result.getDuration());
	}

	@Test
	public void canFindIndividualCoursesViaJson() {
		String id = "AB12";
		client.get()
				.uri("/courses/" + id)
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.title").isEqualTo("Programming in Scala")
				.jsonPath("$.difficulty").isEqualTo("BEGINNER")
				.jsonPath("$.duration").isEqualTo(4);
	}

	@Test
	@DirtiesContext
	public void canDeleteCourses() {
		String id = "CD34";
		client.delete()
				.uri("/courses/" + id)
				.exchange()
				.expectStatus().isOk();

		client.get()
				.uri("/courses/" + id)
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	@DirtiesContext
	public void cannotDeleteScalaCourses() {
		String id = "AB12";
		client.delete()
				.uri("/courses/" + id)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	@DirtiesContext
	public void canAddCourses() {
		String id = "YZ89";
		Course course = new Course(id, "Advanced Haskell", CourseDifficulty.ADVANCED, 5);
		client.put()
				.uri("/courses/" + id)
				.body(fromObject(course))
				.exchange()
				.expectStatus().isOk();

		client.get()
				.uri("/courses/" + id)
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk();

		client.get()
				.uri("/courses")
				.header("Content-Type", MediaType.TEXT_EVENT_STREAM_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Course.class)
                .hasSize(13);
	}
}

