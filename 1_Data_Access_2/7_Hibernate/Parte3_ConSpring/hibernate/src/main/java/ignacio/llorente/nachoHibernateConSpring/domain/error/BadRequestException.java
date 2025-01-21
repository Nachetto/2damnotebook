package ignacio.llorente.nachoHibernateConSpring.domain.error;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}