package com.instil.controllers;

import com.instil.DeletionException;
import com.instil.model.Course;
import com.instil.model.CourseDifficulty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.http.MediaType;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/courses")
class CoursesController {

    @Resource(name = "portfolio")
    private Map<String, Course> portfolio;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Course>> allCourses() {
        if (portfolio.isEmpty()) {
            return notFound().build();
        } else {
            Flux<Course> coursesFlux = Flux
                    .fromIterable(portfolio.values())
                    .delayElements(Duration.ofMillis(250));
            return ok(coursesFlux);
        }
    }

    @GetMapping(value = "/byDifficulty/{difficulty}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<Course>> coursesByDifficulty(@PathVariable("difficulty") CourseDifficulty difficulty) {
        if (portfolio.isEmpty()) {
            return notFound().build();
        } else {
            Flux<Course> coursesFlux = Flux
                    .fromIterable(portfolio.values())
                    .filter(c -> c.getDifficulty() == difficulty)
                    .delayElements(Duration.ofMillis(250));
            return ok(coursesFlux);
        }
    }

    @PutMapping(value = "/{id}",
            consumes = "application/json",
            produces = MediaType.TEXT_PLAIN_VALUE)
    Mono<String> addOrUpdateCourse(@RequestBody Mono<Course> mono) {
        return mono.map(course -> {
            portfolio.put(course.getId(), course);
            return "Course updated";
        });
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Mono<Course>> singleCourse(@PathVariable("id") String id) {
        Course course = portfolio.get(id);
        if (course != null) {
            return ok(Mono.just(course));
        } else {
            return notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<Mono<String>> deleteById(@PathVariable("id") String id) {
        Course course = portfolio.get(id);
        if (course != null) {
            if (course.getTitle().contains("Scala")) {
                throw new DeletionException("Cannot remove Scala courses!");
            }
            portfolio.remove(id);
            return ok(Mono.just("Removed " + id));
        } else {
            return notFound().build();
        }
    }
}

