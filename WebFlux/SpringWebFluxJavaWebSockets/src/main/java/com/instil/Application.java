package com.instil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instil.model.Course;
import com.instil.model.CourseDifficulty;
import com.instil.model.CourseUpdateMessage;
import com.instil.model.CourseUpdateType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.util.UriTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Stream;

import static com.instil.model.CourseUpdateType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource(name = "portfolio")
    private Map<String, Course> portfolio;

    @Resource(name="courses")
    private PairedQueueAndFlux<Course> coursesFlux;

    @Resource(name="courseUpdates")
    private PairedQueueAndFlux<CourseUpdateMessage> updatesFlux;

    @Bean
    WebSocketHandlerAdapter buildAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketMappings() {
        var handler = new SimpleUrlHandlerMapping();
        handler.setUrlMap(Map.of(
                "/courses", allCoursesHandler(),
                "/courses/byId/{id}", singleCourseHandler(),
                "/courses/byDifficulty", coursesByDifficultyHandler(),
                "/courses/updates/all", courseUpdatesHandler()
        ));
        //This property must be set
        handler.setOrder(1);
        return handler;
    }

    @Bean
    public RouterFunction<ServerResponse> standardMappings() {
        return route().path("/courses", builder -> builder
                .PUT("/{id}", this::addOrUpdateCourse)
                .DELETE("/{id}", this::deleteCourse)
        ).build();
    }

    private WebSocketHandler courseUpdatesHandler() {
        return session -> {
            Flux<WebSocketMessage> data = updatesFlux.getBroadcaster()
                    .map(update -> convertToMessage(session, update));
            return session.send(data);
        };
    }

    private WebSocketHandler singleCourseHandler() {
        return session -> {
            String id = extractCourseId(session);
            Mono<WebSocketMessage> data = Mono.just(portfolio.get(id))
                    .map(course -> convertToMessage(session, course));
            return session.send(data);
        };
    }

    private WebSocketHandler allCoursesHandler() {
        return session -> {
            Flux<Course> existing = Flux.fromIterable(portfolio.values());
            Flux<WebSocketMessage> data = existing
                    .concatWith(coursesFlux.getBroadcaster())
                    .delayElements(Duration.ofMillis(250))
                    .map(course -> convertToMessage(session, course));
            return session.send(data);
        };
    }

    private WebSocketHandler coursesByDifficultyHandler() {
        return session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(CourseDifficulty::valueOf)
                .flatMap(difficulty -> {
                    Stream<Course> courses = filterCoursesByDifficulty(difficulty);
                    Flux<WebSocketMessage> data = Flux.fromStream(courses)
                        .delayElements(Duration.ofMillis(250))
                        .map(course -> convertToMessage(session, course));
                    return session.send(data);
                }).then();
    }

    private Stream<Course> filterCoursesByDifficulty(CourseDifficulty difficulty) {
        return portfolio.values()
                .stream()
                .filter(c -> c.getDifficulty() == difficulty);
    }

    private String extractCourseId(WebSocketSession session) {
        var nettySession = (ReactorNettyWebSocketSession) session;
        var template = new UriTemplate("/courses/byId/{id}");
        var parameters = template.match(nettySession.getHandshakeInfo().getUri().getPath());
        return parameters.get("id");
    }

    private Mono<ServerResponse> addOrUpdateCourse(ServerRequest request) {
        return request
                .bodyToMono(Course.class)
                .flatMap(course -> {
                    String id = course.getId();
                    if(!portfolio.containsKey(id)) {
                        coursesFlux.enqueue(course);
                        updatesFlux.enqueue(new CourseUpdateMessage(INSERTED, id));
                    } else {
                        updatesFlux.enqueue(new CourseUpdateMessage(UPDATED, id));
                    }
                    portfolio.put(course.getId(), course);
                    return ok().body(fromValue("Course Added Or Updated"));
                });
    }

    private String removeCourse(Course course) {
        String id = course.getId();

        portfolio.remove(id);
        updatesFlux.enqueue(new CourseUpdateMessage(DELETED, id));
        return String.format("Removed %s", id);
    }

    private Mono<ServerResponse> deleteCourse(ServerRequest request) {
        String id = request.pathVariable("id");
        Course course = portfolio.get(id);
        if (course != null) {
            return ok().body(fromValue(removeCourse(course)));
        } else {
            return notFound().build();
        }
    }

    private <T> WebSocketMessage convertToMessage(WebSocketSession session, T input) {
        var data = convertToJson(input);
        return session.textMessage(data);
    }

    private <T> String convertToJson(T input) {
        var mapper = new ObjectMapper();
        var result = "";
        try {
            result = mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to convert object to JSON: " + e);
        }
        return result;
    }
}
