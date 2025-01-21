package ignacio.llorente.nachoHibernateConSpring.domain.error;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}