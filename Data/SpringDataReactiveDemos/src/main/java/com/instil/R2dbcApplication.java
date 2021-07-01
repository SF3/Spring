package com.instil;

import com.instil.entities.Course;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class R2dbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcApplication.class, args);
    }

    @Bean
    public CommandLineRunner runDemo(CourseRepository courses,
                                     DeliveryRepository deliveries) {
        return args -> {
            showCoreFeatures(courses, deliveries);
            showCustomFinderMethods(courses);
        };
    }

    private void showCoreFeatures(CourseRepository courses, DeliveryRepository deliveries) {
        Flux.just(
                printSingleCourse(courses),
                printAllCourses(courses),
                addNewCourse(courses),
                printAllCourses(courses),
                removeCourse(courses),
                printAllCourses(courses)
        ).subscribe(Mono::block);
    }

    private void showCustomFinderMethods(CourseRepository courses) {
        Flux.just(
                findCoursesByType(courses),
                findCoursesByTitle(courses),
                findCoursesByTitleContaining(courses),
                findCoursesByTypeAndTitle(courses),
                findCourseNumbers(courses)
        ).subscribe(Mono::block);
    }

    private Mono<Void> printSingleCourse(CourseRepository repository) {
        final var msg = "Course 'AB12' has the title: '%s'\n";

        Mono<Course> mono = repository.findById("AB12");
        return mono.map(Course::getTitle)
                .doOnNext(title -> print(msg, title))
                .then();

    }

    private Mono<Void> printAllCourses(CourseRepository repository) {
        return repository.findAll()
                .doFirst(() -> print("Details of all the courses are:\n"))
                .doOnNext(c -> print("\t[%s] %s\n", c.getNumber(), c.getTitle()))
                .then();
    }

    private Mono<Void> addNewCourse(CourseRepository repository) {
        Course course = new Course();
        course.setTitle("Advanced Scala");
        course.setType("advanced");
        course.setNumber("YZ89");

        return repository.save(course)
                .doFinally(signal -> {
                    course.markAsSaved();
                    print("Course YZ89 added\n");
                })
                .then();
    }

    private Mono<Void> removeCourse(CourseRepository repository) {
        return repository
                .findById("YZ89")
                .flatMap(repository::delete)
                .doFinally(x -> print("Course YZ89 removed\n"));
    }

    private Mono<Void> findCoursesByType(CourseRepository repository) {
        return repository.findByType("Beginners")
                .doFirst(() -> print("Details of all beginners courses are:\n"))
                .doOnNext(c -> print("\t[%s] %s\n", c.getNumber(), c.getTitle()))
                .then();
    }

    private Mono<Void> findCoursesByTitle(CourseRepository repository) {
        return repository.findByTitle("Intro To Java")
                .doFirst(() -> print("Details of courses called 'Intro to Java' are:\n"))
                .doOnNext(c -> print("\t[%s] %s\n", c.getNumber(), c.getTitle()))
                .then();
    }

    private Mono<Void> findCoursesByTitleContaining(CourseRepository repository) {
        return repository.findByTitleContaining("Intro")
                .doFirst(() -> print("Details of courses containing the word 'Intro' are:\n"))
                .doOnNext(c -> print("\t[%s] %s\n", c.getNumber(), c.getTitle()))
                .then();
    }

    private Mono<Void> findCoursesByTypeAndTitle(CourseRepository repository) {
        return repository.findByTypeAndTitleContaining("Beginners", "Java")
                .doFirst(() -> print("Details of Beginners courses containing 'Java' are:\n"))
                .doOnNext(c -> print("\t[%s] %s\n", c.getNumber(), c.getTitle()))
                .then();
    }

    private Mono<Void> findCourseNumbers(CourseRepository repository) {
        return repository.customQuery()
                .doFirst(() -> print("The numbers of all the Courses are:\n"))
                .doOnNext(courseNum -> print("\t%s\n", courseNum))
                .then();
    }

    public static void print(String msg, Object... args) {
        System.out.printf(msg, args);
    }
}
