package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDAO {
    List<Payment> getAll();
}
