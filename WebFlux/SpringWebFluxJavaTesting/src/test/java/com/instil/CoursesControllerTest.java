package com.instil;

import com.instil.controllers.CoursesController;
import com.instil.model.Course;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static com.instil.model.CourseDifficulty.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(CoursesController.class)
@Import(ApplicationConfig.class)
public class CoursesControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldReturn12Courses() throws Exception {
        client.get()
                .uri("/courses")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Course.class)
                .hasSize(12);
    }

    @Test
    public void shouldReturnCoursesByID() {
        var result = client.get()
                .uri("/courses")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Course.class);

        var ids = result.getResponseBody().map(c -> c.getId());
        StepVerifier.create(ids)
                .expectNext("AB12")
                .expectNext("CD34")
                .expectNextCount(9)
                .expectNext("WX34")
                .verifyComplete();
    }

    @Test
    public void shouldFindCoursesByDifficulty() {
        client.get()
                .uri("/courses/byDifficulty/ADVANCED")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Course.class)
                .hasSize(4)
                .consumeWith(result -> {
                    var courses = result.getResponseBody();
                    courses.forEach(c -> assertEquals(ADVANCED, c.getDifficulty()));
                });
    }

    @Test
    public void shouldFindCourseByID() {
        client.get()
                .uri("/courses/AB12")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").value(Matchers.is("AB12"))
                .jsonPath("$.title").value(Matchers.is("Programming in Scala"))
                .jsonPath("$.difficulty").value(Matchers.is("BEGINNER"))
                .jsonPath("$.duration").value(Matchers.is(4));
    }

    @Test
    @DirtiesContext
    public void coursesCanBeRemoved() {
        client.delete()
                .uri("/courses/CD34")
                .exchange()
                .expectStatus().isOk();

        client.get()
                .uri("/courses")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Course.class)
                .hasSize(11)
                .doesNotContain(new Course("CD34", "Machine Learning in Python", INTERMEDIATE, 3));
    }

    @Test
    @DirtiesContext
    public void coursesCanBeAdded() {
        var newCourse = new Course("YZ98", "Extra Hard Haskell", ADVANCED, 5);

        client.put()
                .uri("/courses/" + newCourse.getId())
                .bodyValue(newCourse)
                .exchange()
                .expectStatus().isOk();

        client.get()
                .uri("/courses")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Course.class)
                .hasSize(13)
                .contains(newCourse);
    }
}
