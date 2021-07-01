package demos.spring.boot.rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "listOfCourses")
public class CourseList {
    private Collection<Course> courses;

    public CourseList() {
    }

    public CourseList(Collection<Course> courses) {
        this.courses = courses;
    }

    @XmlElement(name = "entry")
    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }
}
