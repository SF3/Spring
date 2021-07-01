package demos.spring.boot.rest.extended;

import demos.spring.boot.rest.DeletionException;
import demos.spring.boot.rest.model.Course;
import demos.spring.boot.rest.model.CourseList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController("extendedCoursesController")
@RequestMapping("/extended/courses")
public class CoursesController {
    private Map<String, Course> portfolio;

    @Resource(name = "portfolio")
    public void setPortfolio(Map<String, Course> portfolio) {
        this.portfolio = portfolio;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<CourseList> viewAllAsXml() {
        if (portfolio.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        } else {
            return new ResponseEntity<>(new CourseList(portfolio.values()), OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Course>> viewAllAsJson() {
        if (portfolio.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        } else {
            Collection<Course> courses = portfolio.values();
            HttpHeaders headers = new HttpHeaders();
            headers.set("NumCourses", String.valueOf(courses.size()));
            return new ResponseEntity<>(courses, headers, OK);
        }
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void addOrUpdateCourseViaJson(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/xml")
    public void addOrUpdateCourseViaXml(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Course> fetchCourseDetailsAsJson(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            return new ResponseEntity<>(portfolio.get(id), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<Course> fetchCourseDetailsAsXml(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            return new ResponseEntity<>(portfolio.get(id), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteACourse(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            if (portfolio.get(id).getTitle().contains("Scala")) {
                throw new DeletionException("Cannot remove Scala courses!");
            }
            portfolio.remove(id);
            return new ResponseEntity<>("[\"Removed " + id + "\"]", OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
