package com.instil;

import com.instil.entities.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, String> {
    List<Course> findByType(String type);
    List<Course> findByTitle(String title);
    List<Course> findByTitleContaining(String text);
    List<Course> findByTypeAndTitleContaining(String type, String titleText);
    @Query("SELECT c.number FROM Course c")
    List<String> findByCustomQueryOne();
}
