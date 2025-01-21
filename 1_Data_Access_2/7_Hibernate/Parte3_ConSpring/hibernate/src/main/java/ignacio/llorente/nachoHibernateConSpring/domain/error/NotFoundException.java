package ignacio.llorente.nachoHibernateConSpring.domain.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}