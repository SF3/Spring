package com.instil.mvc.services;

import com.instil.mvc.model.Course;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    @GetMapping(produces = "application/json")
    public List<Course> viewAllAsJson() {
        List<Course> retval = new ArrayList<Course>(portfolio.values());
        return retval;
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Course addOrUpdateCourseViaJson(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
        return newCourse;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Course fetchCourseDetailsAsJson(@PathVariable("id") String id) {
        return portfolio.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteACourse(@PathVariable("id") String id) {
        portfolio.remove(id);
    }

    @Resource(name = "portfolio")
    public void setPortfolio(Map<String, Course> portfolio) {
        this.portfolio = portfolio;
    }

    private Map<String, Course> portfolio;
}
