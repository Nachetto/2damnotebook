package dev.nacho.ghub.domain.error;


import static dev.nacho.ghub.common.ErrorConstants.INVALID_REQUEST;

public class BadRequestException extends RuntimeException {


    public BadRequestException(String message) {
        super(INVALID_REQUEST + message);
    }
}
