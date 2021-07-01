package com.instil;

import com.instil.entities.Course;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CourseRepository extends ReactiveCrudRepository<Course, String> {
    Flux<Course> findByType(String type);
    Flux<Course> findByTitle(String title);
    Flux<Course> findByTitleContaining(String text);
    Flux<Course> findByTypeAndTitleContaining(String type, String titleText);

    @Query("SELECT c.CourseNum FROM Courses c")
    Flux<String> customQuery();
}
