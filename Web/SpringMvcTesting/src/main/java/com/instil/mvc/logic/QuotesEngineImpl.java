package com.instil.mvc.logic;

import com.instil.mvc.logic.QuotesEngine;
import com.instil.mvc.model.Course;
import com.instil.mvc.model.Quote;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
public class QuotesEngineImpl implements QuotesEngine {

    @Override
    public Optional<Quote> generateQuote(String courseId) {
        return findCourse(courseId)
                .map(this::buildQuote);
    }

    private Quote buildQuote(Course course) {
        double rate = 0;
        switch(course.getDifficulty()) {
            case BEGINNER:
                rate = 1200.0;
                break;
            case INTERMEDIATE:
                rate = 1400.0;
                break;
            case ADVANCED:
                rate = 1600.0;
                break;
        }
        return new Quote(course, course.getDuration() * rate);
    }

    private Optional<Course> findCourse(String courseId) {
        return portfolio.values()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst();
    }

    @Resource(name = "portfolio")
    public void setPortfolio(Map<String, Course> portfolio) {
        this.portfolio = portfolio;
    }

    private Map<String, Course> portfolio;
}
