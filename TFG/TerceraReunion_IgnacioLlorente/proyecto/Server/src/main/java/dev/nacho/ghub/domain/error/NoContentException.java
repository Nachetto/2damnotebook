package dev.nacho.ghub.domain.error;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}