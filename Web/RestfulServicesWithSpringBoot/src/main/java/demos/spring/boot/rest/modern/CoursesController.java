package demos.spring.boot.rest.modern;

import demos.spring.boot.rest.DeletionException;
import demos.spring.boot.rest.model.Course;
import demos.spring.boot.rest.model.CourseDifficulty;
import demos.spring.boot.rest.model.CourseList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController("modernCoursesController")
@RequestMapping("/modern/courses")
public class CoursesController {
    private Map<String, Course> portfolio;

    @Resource(name = "portfolio")
    public void setPortfolio(Map<String, Course> portfolio) {
        this.portfolio = portfolio;
    }

    @GetMapping(produces = "application/xml")
    public ResponseEntity<CourseList> viewAllAsXml(@RequestParam("type") Optional<CourseDifficulty> type) {
        if (portfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Collection<Course> courses = new HashSet<>(portfolio.values());
            type.ifPresent(t -> courses.removeIf(c -> !c.getDifficulty().equals(t)));
            return ResponseEntity.ok(new CourseList(courses));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<Course>> viewAllAsJson(@RequestParam("type") Optional<CourseDifficulty> type) {
        if (portfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Collection<Course> courses = new HashSet<>(portfolio.values());
            type.ifPresent(t -> courses.removeIf(c -> !c.getDifficulty().equals(t)));
            HttpHeaders headers = new HttpHeaders();
            headers.set("NumCourses", String.valueOf(courses.size()));
            return new ResponseEntity<>(courses, headers, OK);
        }
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = "application/json")
    public void addOrUpdateCourseViaJson(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = "application/xml")
    public void addOrUpdateCourseViaXml(@RequestBody Course newCourse) {
        portfolio.put(newCourse.getId(), newCourse);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Course> fetchCourseDetailsAsJson(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            return ResponseEntity.ok(portfolio.get(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<Course> fetchCourseDetailsAsXml(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            return ResponseEntity.ok(portfolio.get(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteACourse(@PathVariable("id") String id) {
        if (portfolio.containsKey(id)) {
            if (portfolio.get(id).getTitle().contains("Scala")) {
                throw new DeletionException("Cannot remove Scala courses!");
            }
            portfolio.remove(id);
            return ResponseEntity.ok("[\"Removed " + id + "\"]");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
