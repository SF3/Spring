package com.instil;

import com.instil.model.Course;
import com.instil.model.CourseDifficulty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080/courses");
    }

    @Bean
    CommandLineRunner console(WebClient client) {
        return args -> {
            Scanner scanner = new Scanner(System.in).useDelimiter("\n");
            int choice;
            do {
                printMenu();
                choice = readInt(scanner);
                performAction(choice, scanner, client);
            } while (choice != 6);
        };
    }

    private void performAction(int choice, Scanner scanner, WebClient client) {
        switch (choice) {
            case 1:
                showAll(client);
                break;
            case 2:
                showByDifficulty(scanner, client);
                break;
            case 3:
                showById(scanner, client);
                break;
            case 4:
                createOrUpdate(scanner, client);
                break;
            case 5:
                deleteById(scanner, client);
                break;
            case 6:
                print("Goodbye...");
                break;
            default:
                printError("Invalid choice");
        }
    }

    private void deleteById(Scanner scanner, WebClient client) {
        String id = readId(scanner);
        var responseAsMono = client
                .delete()
                .uri("/" + id)
                .accept(MediaType.TEXT_PLAIN)
                .exchange();
        var response = responseAsMono.block();
        if (response.statusCode() == HttpStatus.OK) {
            String message = response
                    .bodyToMono(String.class)
                    .block();
            print(message);
        } else {
            printError("Could not remove course!");
        }
    }

    private void createOrUpdate(Scanner scanner, WebClient client) {
        Course course = readCourse(scanner);
        var response = client
                .put()
                .uri("/" + course.getId())
                .bodyValue(course)
                .accept(MediaType.TEXT_PLAIN)
                .exchange();
        String message = response
                .block()
                .bodyToMono(String.class)
                .block();
        print(message);
    }

    private void showById(Scanner scanner, WebClient client) {
        String id = readId(scanner);
        var response = client
                .get()
                .uri("/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
        Course selected = response.block()
                .bodyToMono(Course.class)
                .block();
        printCourse(selected);
    }

    private void showByDifficulty(Scanner scanner, WebClient client) {
        CourseDifficulty difficulty = readDifficulty(scanner);
        print("All the courses of type " + difficulty);
        var response = client
                .get()
                .uri("/byDifficulty/" + difficulty)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange();
        response.block()
                .bodyToFlux(Course.class)
                .doOnNext(this::printCourse)
                .blockLast();
    }

    private void showAll(WebClient client) {
        print("All the courses");
        var response = client
                .get()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange();
        response.block()
                .bodyToFlux(Course.class)
                .doOnNext(this::printCourse)
                .blockLast();
    }

    private void printCourse(Course course) {
        print(String.format("\t%s (%s)", course.getTitle(), course.getId()));
    }

    private int readDuration(Scanner scanner) {
        print("Enter a duration");
        return readInt(scanner);
    }

    private int readInt(Scanner scanner) {
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            printError("Try again...");
        }
        throw new IllegalStateException("Scanner somehow failed");
    }

    private Course readCourse(Scanner scanner) {
        String id = readId(scanner);
        String title = readTitle(scanner);
        CourseDifficulty difficulty = readDifficulty(scanner);
        int duration = readDuration(scanner);

        return new Course(id, title, difficulty, duration);
    }

    private String readTitle(Scanner scanner) {
        print("Enter a course title");
        if (scanner.hasNext()) {
            return scanner.next();
        }
        throw new IllegalStateException("Scanner somehow failed");
    }

    private String readId(Scanner scanner) {
        print("Enter a course id");
        while (scanner.hasNext()) {
            if (scanner.hasNext("[A-Z]{2}[0-9]{2}")) {
                return scanner.next();
            }
            scanner.next(); //disregard erroneous input
            printError("Try again...");
        }
        throw new IllegalStateException("Scanner somehow failed");
    }

    private CourseDifficulty readDifficulty(Scanner scanner) {
        print("Enter a difficulty");
        while (scanner.hasNext()) {
            try {
                return CourseDifficulty.valueOf(scanner.next());
            } catch (IllegalArgumentException ex) {
                printError("Try again...");
            }
        }
        throw new IllegalStateException("Scanner somehow failed");
    }

    private void printMenu() {
        print("Choose from the following:");
        print("1) View all the courses");
        print("2) View courses by difficulty");
        print("3) View a single course");
        print("4) Create or update a course");
        print("5) Delete a course");
        print("6) Exit");
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static void printError(String msg) {
        System.err.println(msg);
    }
}
