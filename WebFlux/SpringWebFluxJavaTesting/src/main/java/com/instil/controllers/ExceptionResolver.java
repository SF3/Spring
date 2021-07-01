package com.instil.controllers;

import com.instil.DeletionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@RestController
public class ExceptionResolver {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DeletionException.class)
    Mono<String> deleteError(Exception ex) {
        String msg = "{ \"errorType\": \"%s\", \"message\": \"%s\" }";
        return Mono.just(String.format(
                msg,
                ex.getClass().getSimpleName(),
                ex.getMessage()));
    }
}
