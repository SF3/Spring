package com.instil.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class WebSocketBasedService {
    @Resource(name = "flintstones")
    private List<String> flintstones;

    @Resource(name = "simpsons")
    private List<String> simpsons;

    @Bean
    WebSocketHandlerAdapter buildAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping buildMappings() {
        var handler = new SimpleUrlHandlerMapping();
        handler.setUrlMap(Map.of("/demo3", buildSimpleHandler()));
        handler.setUrlMap(Map.of("/demo4", buildComplexHandler()));
        handler.setOrder(1);
        return handler;
    }

    WebSocketHandler buildSimpleHandler() {
        return session -> {
            Flux<WebSocketMessage> output = Flux
                    .fromIterable(simpsons)
                    .map(session::textMessage)
                    .delayElements(Duration.ofSeconds(1));

            Flux<String> input = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doOnNext(str -> System.out.printf("Received: %s\n", str));

            final Mono<Void> completionToken = session.send(output).and(input);
            return completionToken;
        };
    }
    WebSocketHandler buildComplexHandler() {
        return session -> session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(name -> {
                    System.out.printf("Received: %s\n", name);
                    Flux<String> data = selectData(name);
                    return session.send(data.map(session::textMessage));
                }).then();
    }

    private Flux<String> selectData(String name) {
        List<String> data;
        switch(name) {
            case "simpsons":
                data = simpsons;
                break;
            case "flintstones":
                data = flintstones;
                break;
            default:
                data = List.of("No characters!");
        }

        return Flux
                .fromIterable(data)
                .delayElements(Duration.ofSeconds(1));
    }
}
