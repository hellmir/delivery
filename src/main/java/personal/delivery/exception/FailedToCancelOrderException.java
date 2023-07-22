package personal.delivery.exception;

public class FailedToCancelOrderException extends RuntimeException {
    public FailedToCancelOrderException(String message) {
        super(message);
    }
}
