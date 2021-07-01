package com.instil;

import com.instil.model.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.instil.model.CourseDifficulty.*;

@Configuration
public class ApplicationConfig {
    @Bean(name = "portfolio")
    @Scope("prototype")
    public Map<String, Course> buildPortfolio() {
        Map<String, Course> portfolio = new TreeMap<>();
        portfolio.put("AB12", new Course("AB12", "Programming in Scala", BEGINNER, 4));
        portfolio.put("CD34", new Course("CD34", "Machine Learning in Python", INTERMEDIATE, 3));
        portfolio.put("EF56", new Course("EF56", "Advanced Kotlin Coding", ADVANCED, 2));
        portfolio.put("GH78", new Course("GH78", "Intro to Domain Driven Design", BEGINNER, 3));
        portfolio.put("IJ90", new Course("IJ90", "Database Access with JPA", INTERMEDIATE, 3));
        portfolio.put("KL12", new Course("KL12", "Functional Design Patterns in F#", ADVANCED, 2));
        portfolio.put("MN34", new Course("MN34", "Building Web UIs with Angular", BEGINNER, 4));
        portfolio.put("OP56", new Course("OP56", "Version Control with Git", INTERMEDIATE, 1));
        portfolio.put("QR78", new Course("QR78", "SQL Server Masterclass", ADVANCED, 2));
        portfolio.put("ST90", new Course("ST90", "Go Programming for Beginners", BEGINNER, 5));
        portfolio.put("UV12", new Course("UV12", "Coding with Lock Free Algorithms", INTERMEDIATE, 2));
        portfolio.put("WX34", new Course("WX34", "Coaching Skills for SCRUM Masters", ADVANCED, 3));

        return portfolio;
    }    
}
