package com.hospital_jpa.domain.error;

public class Error_Creating_Object extends RuntimeException {
    public Error_Creating_Object(String message) {
        super(message);
    }
}
