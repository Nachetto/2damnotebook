package dev.nacho.ghub.domain.error;


import static dev.nacho.ghub.common.ErrorConstants.DB_COMMUNICATION_ERROR;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(DB_COMMUNICATION_ERROR +message);
    }
}
