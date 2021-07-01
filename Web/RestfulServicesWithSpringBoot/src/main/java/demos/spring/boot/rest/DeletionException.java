package demos.spring.boot.rest;

@SuppressWarnings("serial")
public class DeletionException extends RuntimeException {
    public DeletionException(String message) {
        super(message);
    }
}
