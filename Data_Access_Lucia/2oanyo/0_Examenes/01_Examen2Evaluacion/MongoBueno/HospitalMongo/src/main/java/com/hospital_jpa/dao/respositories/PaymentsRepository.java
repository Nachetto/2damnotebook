package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.Payment;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class PaymentsRepository implements com.hospital_jpa.dao.interfaces.PaymentsRepository {

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
        return payments;
    }
}
