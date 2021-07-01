package com.instil;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class HelloSpringBootApplication {

    @Bean(name = "helloMsg")
    public Mono<String> buildMsg() {
        return Mono.just("Hello Spring Boot!");
    }

    @Bean
    public CommandLineRunner startup(@Qualifier("helloMsg") Mono<String> message) {
        return args -> message.subscribe(
                System.out::println,
                error -> System.err.println("Whoops: " + error.getMessage()),
                () -> System.out.println("...bye for now.")
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }
}
