package com.instil.mvc;

import com.instil.mvc.model.Course;
import com.instil.mvc.services.CoursesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.instil.mvc.model.CourseDifficulty.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CoursesControllerUnitTest {
	private CoursesController controller;

	@BeforeEach
	public void start() {
		controller = new CoursesController();
		controller.setPortfolio(buildTestPortfolio());
	}

	@Test
	public void allCoursesCanBeFound() {
		List<Course> courses = controller.viewAllAsJson();
		assertEquals(3, courses.size());
	}

	@Test
	public void coursesCanBeFoundByID() {
		Course course = controller.fetchCourseDetailsAsJson("AB12");
		assertEquals("AB12", course.getId());
	}

	@Test
	public void coursesCanBeRemoved() {
		controller.deleteACourse("AB12");
		List<Course> courses = controller.viewAllAsJson();
		assertEquals(2, courses.size());
		courses.stream()
				.filter(c -> c.getId().equals("AB12"))
				.findFirst()
				.ifPresent(c -> fail("Course not deleted"));
	}

	@Test
	public void coursesCanBeAdded() {
		controller.addOrUpdateCourseViaJson(new Course("YZ89", "Advanced Scala", ADVANCED, 2));
		List<Course> courses = controller.viewAllAsJson();
		assertEquals(4, courses.size());
		courses.stream()
				.filter(c -> c.getId().equals("YZ89"))
				.findFirst()
				.ifPresentOrElse(
						c -> assertEquals("Advanced Scala", c.getTitle()),
						() -> fail("Course not found"));
	}

	private Map<String, Course> buildTestPortfolio() {
		HashMap<String, Course> tmp = new HashMap<>();
		tmp.put("AB12", new Course("AB12", "Intro to Scala", BEGINNER, 4));
		tmp.put("CD34", new Course("CD34", "Meta-Programming in Ruby", ADVANCED, 2));
		tmp.put("EF56", new Course("EF56", "Writing MySql Stored Procedures", INTERMEDIATE, 1));
		return tmp;
	}
}
