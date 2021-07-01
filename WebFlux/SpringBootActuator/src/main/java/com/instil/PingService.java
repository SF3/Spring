package com.instil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/ping")
public class PingService {
    @RequestMapping(value = "/{timeout}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Mono<String>> ping(@PathVariable("timeout") int timeout) throws Exception {
        System.out.println("Ping service called with timeout of " + timeout);
        if (timeout > 10) {
            String json = "[\"Errorback - timeout too high!\"]";
            return new ResponseEntity<>(Mono.just(json), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        Thread.sleep(timeout * 1000);
        String json = "[\"Pingback after " + timeout + "\"]";
        return ok(Mono.just(json));
    }
}
