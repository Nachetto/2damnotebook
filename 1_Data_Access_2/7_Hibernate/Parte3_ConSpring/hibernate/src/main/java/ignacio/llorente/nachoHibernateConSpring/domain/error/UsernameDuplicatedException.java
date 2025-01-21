package ignacio.llorente.nachoHibernateConSpring.domain.error;

public class UsernameDuplicatedException extends RuntimeException {
    public UsernameDuplicatedException(String message) {
        super(message);
    }
}
