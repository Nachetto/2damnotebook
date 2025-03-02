package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class PaymentsRepositoryJPA implements com.hospital_jpa.dao.interfaces.PaymentsRepository {

    private final JPAUtil jpaUtil;

    public PaymentsRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public int save(Payment payment) {
        return 0;
    }

    @Override
    public void update(Payment payment) {

    }

    @Override
    public boolean delete(int paymentId) {
        return false;
    }

    @Override
    public List<Payment> getPaymentsByPatient() {
        List<Payment> payments = new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            payments = em.createNamedQuery("getPaymentsByPatient",Payment.class).getResultList();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return payments;
    }
}
