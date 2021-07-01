package demos.spring.boot.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "course")
public class Course {
    private String id;
    private String title;
    private CourseDifficulty difficulty;
    private int duration;

    public Course() {
        super();
    }

    public Course(String id, String title, CourseDifficulty difficulty, int duration) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public CourseDifficulty getDifficulty() {
        return difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void setDifficulty(CourseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
