package demos.spring.boot.flights.errors;

@SuppressWarnings("serial")
public class NotImplementedYetException extends RuntimeException {
    public NotImplementedYetException(String message) {
        super(message);
    }
}
