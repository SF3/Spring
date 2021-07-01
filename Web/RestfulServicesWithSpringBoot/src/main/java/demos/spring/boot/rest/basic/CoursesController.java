package demos.spring.boot.rest.basic;

import demos.spring.boot.rest.model.Course;
import demos.spring.boot.rest.model.CourseList;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@RestController("basicCoursesController")
@RequestMapping("/basic/courses")
public class CoursesController {
    private Map<String, Course> portfolio;

    @Resource(name = "portfolio")
    public void setPortfolio(Map<String, Course> portfolio) {
        this.portfolio = portfolio;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml")
    public CourseList viewAllAsXml() {
        return new CourseList(portfolio.values());
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<Course> viewAllAsJson() {
        return portfolio.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Course addOrUpdateCourseViaJson(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
        return newCourse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/xml")
    public String addOrUpdateCourseViaXml(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
        return newCourse.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Course fetchCourseDetailsAsJson(@PathVariable("id") String id) {
        return portfolio.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/xml")
    public Course fetchCourseDetailsAsXml(@PathVariable("id") String id) {
        return portfolio.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteACourse(@PathVariable("id") String id) {
        portfolio.remove(id);
    }
}
