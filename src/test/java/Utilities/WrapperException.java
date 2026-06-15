package Utilities;

/**
 * A simple runtime exception to wrap lower-level Selenium and wait-related
 * exceptions so caller code can handle failures from wrapper utilities in a
 * consistent way.
 */
public class WrapperException extends RuntimeException {
    public WrapperException(String message) {
        super(message);
    }

    public WrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
