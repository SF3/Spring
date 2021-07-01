package com.instil;

import com.instil.model.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


import org.springframework.web.reactive.function.BodyInserters.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import javax.annotation.Resource;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource(name = "portfolio")
    private Map<String, Course> portfolio;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route().path("/courses", builder -> builder
                .GET("/", accept(TEXT_EVENT_STREAM), this::allCourses)
                .GET("/{id}", accept(APPLICATION_JSON), this::singleCourse)
                .GET("/byDifficulty/{difficulty}", accept(TEXT_EVENT_STREAM), this::coursesByDifficulty)
                .PUT("/{id}", this::addOrUpdateCourse)
                .DELETE("/{id}", this::deleteCourse)
        ).build();
    }

    private Mono<ServerResponse> allCourses(ServerRequest request) {
        if (portfolio.isEmpty()) {
            return notFound().build();
        } else {
            Flux<Course> coursesFlux = Flux
                    .fromIterable(portfolio.values())
                    .delayElements(Duration.ofMillis(250));
            return ok().contentType(TEXT_EVENT_STREAM)
                    .body(fromPublisher(coursesFlux, Course.class));
        }
    }

    private Mono<ServerResponse> coursesByDifficulty(ServerRequest request) {
        String difficulty = request.pathVariable("difficulty");
        if (portfolio.isEmpty()) {
            return notFound().build();
        } else {
            Flux<Course> coursesFlux = Flux
                    .fromIterable(portfolio.values())
                    .filter(c ->
                            c.getDifficulty()
                                    .toString()
                                    .equalsIgnoreCase(difficulty))
                    .delayElements(Duration.ofMillis(250));
            return ok().contentType(TEXT_EVENT_STREAM)
                    .body(fromPublisher(coursesFlux, Course.class));
        }
    }

    private Mono<ServerResponse> addOrUpdateCourse(ServerRequest request) {
        return request
                .bodyToMono(Course.class)
                .flatMap(course -> {
                    portfolio.put(course.getId(), course);
                    return ok().body(fromObject("Course Updated"));
                });
    }

    private Mono<ServerResponse> singleCourse(ServerRequest request) {
        String id = request.pathVariable("id");
        Course course = portfolio.get(id);
        if (course != null) {
            return ok().body(fromObject(course));
        } else {
            return notFound().build();
        }
    }

    private String removeCourse(Course course) {
        portfolio.remove(course.getId());
        return String.format("Removed %s", course.getId());
    }

    private Mono<ServerResponse> deleteCourse(ServerRequest request) {
        String id = request.pathVariable("id");
        Course course = portfolio.get(id);
        if (course != null) {
            return ok().body(fromObject(removeCourse(course)));
        } else {
            return notFound().build();
        }
    }
}
