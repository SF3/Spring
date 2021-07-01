package com.instil.webflux;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/demo1")
public class AnnotationBasedService {

    @Resource(name = "flintstones")
    private List<String> data;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<String>> flintstones() {
        if (data.isEmpty()) {
            return notFound().build();
        } else {
            Flux<String> coursesFlux = Flux
                    .fromIterable(data)
                    .delayElements(Duration.ofSeconds(1));
            return ok(coursesFlux);
        }
    }
}
