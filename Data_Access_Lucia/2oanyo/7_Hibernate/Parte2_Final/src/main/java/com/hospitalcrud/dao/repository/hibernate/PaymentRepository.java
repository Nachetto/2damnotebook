package com.hospitalcrud.dao.repository.hibernate;

import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.Payment;
import com.hospitalcrud.dao.repository.PaymentDAO;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQueries;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Profile("hibernate")
@Log4j2
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "Payment.getAll", query = "SELECT p FROM Payment p")
})
public class PaymentRepository implements PaymentDAO {
    private final JPAUtil jpautil;
    private EntityManager em;

    @Inject
    public PaymentRepository(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }


    @Override
    public List<Payment> getAll() {
        List<Payment> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Payment.getAll", Payment.class)
                    .getResultList();
            return list;
        } finally {
            if (em != null) em.close();
        }
    }
}
