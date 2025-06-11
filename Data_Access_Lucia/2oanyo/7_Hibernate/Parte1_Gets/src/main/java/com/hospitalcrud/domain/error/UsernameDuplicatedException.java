package com.hospitalcrud.domain.error;

public class UsernameDuplicatedException extends RuntimeException {
    public UsernameDuplicatedException(String message) {
        super(message);
    }
}
