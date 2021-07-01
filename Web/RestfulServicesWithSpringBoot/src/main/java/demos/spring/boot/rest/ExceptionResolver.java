package demos.spring.boot.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@RestController
public class ExceptionResolver {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = DeletionException.class)
    public String deleteError(Exception ex) {
        return String.format("[\"Root cause was - %s\"]", ex.getMessage());
    }
}
