package org.example.nachoHibernateConSpring.domain.error;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}