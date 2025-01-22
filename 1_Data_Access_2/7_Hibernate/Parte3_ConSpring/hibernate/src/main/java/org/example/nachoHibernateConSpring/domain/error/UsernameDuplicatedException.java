package org.example.nachoHibernateConSpring.domain.error;

public class UsernameDuplicatedException extends RuntimeException {
    public UsernameDuplicatedException(String message) {
        super(message);
    }
}
