package com.instil.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
public class DSLBasedService {
    @Resource(name = "simpsons")
    private List<String> simpsons;

    @Resource(name = "flintstones")
    private List<String> flintstones;

    @Bean
    public RouterFunction<ServerResponse> sampleRoutes() {
        return route().path("/demo2", builder -> builder
                .GET("/", accept(APPLICATION_JSON), this::allCharacters)
                .GET("/{id}", accept(APPLICATION_JSON), this::someCharacters)
        ).build();
    }

    private Mono<ServerResponse> allCharacters(ServerRequest request) {
        if (simpsons.isEmpty()) {
            return notFound().build();
        } else {
            Flux<String> charactersFlux = Flux
                    .fromIterable(simpsons)
                    .mergeWith(Flux.fromIterable(flintstones))
                    .delayElements(Duration.ofSeconds(1));
            return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(fromPublisher(charactersFlux, String.class));
        }
    }

    private Mono<ServerResponse> someCharacters(ServerRequest request) {
        String id = request.pathVariable("id");

        List<String> data;
        switch(id) {
            case "simpsons":
                data = simpsons;
                break;
            case "flintstones":
                data = flintstones;
                break;
            default:
                data = List.of("No characters!");
        }

        Flux<String> charactersFlux = Flux
                .fromIterable(data)
                .delayElements(Duration.ofSeconds(1));
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(fromPublisher(charactersFlux, String.class));
    }
}
