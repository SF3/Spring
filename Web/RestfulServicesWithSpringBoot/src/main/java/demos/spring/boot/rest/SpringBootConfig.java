package demos.spring.boot.rest;

import demos.spring.boot.rest.model.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

import static demos.spring.boot.rest.model.CourseDifficulty.*;

@Configuration
public class SpringBootConfig {
    @Bean(name = "portfolio")
    @Scope("prototype")
    public Map<String, Course> buildPortfolio() {
        HashMap<String, Course> portfolio = new HashMap<String, Course>();
        addCourseToMap(new Course("AB12", "Intro to Scala", BEGINNER, 4), portfolio);
        addCourseToMap(new Course("CD34", "JEE Web Development", INTERMEDIATE, 3), portfolio);
        addCourseToMap(new Course("EF56", "Meta-Programming in Ruby", ADVANCED, 2), portfolio);
        addCourseToMap(new Course("GH78", "OO Design with UML", BEGINNER, 3), portfolio);
        addCourseToMap(new Course("IJ90", "Database Access with JPA", INTERMEDIATE, 3), portfolio);
        addCourseToMap(new Course("KL12", "Design Patterns in C#", ADVANCED, 2), portfolio);
        addCourseToMap(new Course("MN34", "Relational Database Design", BEGINNER, 4), portfolio);
        addCourseToMap(new Course("OP56", "Writing MySql Stored Procedures", INTERMEDIATE, 1), portfolio);
        addCourseToMap(new Course("QR78", "Patterns of Parallel Programming", ADVANCED, 2), portfolio);
        addCourseToMap(new Course("ST90", "C++ Programming for Beginners", BEGINNER, 5), portfolio);
        addCourseToMap(new Course("UV12", "UNIX Threading with PThreads", INTERMEDIATE, 2), portfolio);
        addCourseToMap(new Course("WX34", "Writing Linux Device Drivers", ADVANCED, 3), portfolio);

        return portfolio;
    }

    private void addCourseToMap(Course course, Map<String, Course> courses) {
        courses.put(course.getId(), course);
    }
}
