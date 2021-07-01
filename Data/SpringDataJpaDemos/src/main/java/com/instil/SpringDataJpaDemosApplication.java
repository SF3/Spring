package com.instil;

import com.instil.entities.Course;
import com.instil.entities.Delivery;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SpringDataJpaDemosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaDemosApplication.class, args);
	}

	@Bean
	public CommandLineRunner runDemo(CourseRepository courses,
									 DeliveryRepository deliveries) {
		return args -> {
			showCoreFeatures(courses, deliveries);
			showCustomFinderMethods(courses);
		};
	}

	private void showCustomFinderMethods(CourseRepository courses) {
		findCoursesByType(courses);
		findCoursesByTitle(courses);
		findCoursesByTitleContaining(courses);
		findCoursesByTypeAndTitle(courses);
		findCourseNumbers(courses);
	}

	private void showCoreFeatures(CourseRepository courses, DeliveryRepository deliveries) {
		printSingleCourse(courses);
		printAllCourses(courses);
		addNewCourse(courses);
		printAllCourses(courses);
		removeCourse(courses);
		printAllCourses(courses);
		printDeliveryDetails(deliveries);
	}

	private void printSingleCourse(CourseRepository repository) {
		Optional<Course> opt = repository.findById("AB12");
		String title = opt.map(Course::getTitle).orElse("UNKNOWN");
		System.out.printf("\tCourse 'AB12' has the title: '%s'\n", title);
	}

	private void printAllCourses(CourseRepository repository) {
		Iterable<Course> courseList = repository.findAll();
		System.out.println("Details of all the courses are:");
		for (Course c : courseList) {
			System.out.printf("\t[%s] %s\n", c.getNumber(), c.getTitle());
		}
	}

	private void addNewCourse(CourseRepository repository) {
		Course course = new Course();
		course.setTitle("Advanced Scala");
		course.setType("advanced");
		course.setNumber("YZ89");

		repository.save(course);
	}

	private void removeCourse(CourseRepository repository) {
		Optional<Course> opt = repository.findById("YZ89");
		opt.ifPresent(repository::delete);
	}

	private void printDeliveryDetails(DeliveryRepository repository) {
		Optional<Delivery> opt = repository.findById(1004);
		opt.ifPresent(delivery -> {
			String courseTitle = delivery.getCourse().getTitle();
			String programName = delivery.getTrainingProgram().getName();

			String msg = "\tDelivery 1004 is of course '%s' in program '%s'\n";
			System.out.printf(msg, courseTitle, programName);
		});
	}

	private void findCoursesByType(CourseRepository repository) {
		Iterable<Course> courseList = repository.findByType("beginners");
		System.out.println("Details of all beginners courses are:");
		for (Course c : courseList) {
			System.out.printf("\t[%s] %s\n", c.getNumber(), c.getTitle());
		}
	}

	private void findCoursesByTitle(CourseRepository repository) {
		Iterable<Course> courseList = repository.findByTitle("Intro To Java");
		System.out.println("Details of courses called 'Intro to Java' are:");
		for (Course c : courseList) {
			System.out.printf("\t[%s] %s\n", c.getNumber(), c.getTitle());
		}
	}

	private void findCoursesByTitleContaining(CourseRepository repository) {
		Iterable<Course> courseList = repository.findByTitleContaining("Intro");
		System.out.println("Details of courses containing the word 'Intro' are:");
		for (Course c : courseList) {
			System.out.printf("\t[%s] %s\n", c.getNumber(), c.getTitle());
		}
	}

	private void findCoursesByTypeAndTitle(CourseRepository repository) {
		Iterable<Course> courseList = repository.findByTypeAndTitleContaining("Beginners", "Java");
		System.out.println("Details of Beginners courses containing 'Java' are:");
		for (Course c : courseList) {
			System.out.printf("\t[%s] %s\n", c.getNumber(), c.getTitle());
		}
	}

	private void findCourseNumbers(CourseRepository repository) {
		Iterable<String> results = repository.findByCustomQueryOne();
		System.out.println("The numbers of all the Courses are:");
		for (String str : results) {
			System.out.printf("\t%s\n", str);
		}
	}
}
