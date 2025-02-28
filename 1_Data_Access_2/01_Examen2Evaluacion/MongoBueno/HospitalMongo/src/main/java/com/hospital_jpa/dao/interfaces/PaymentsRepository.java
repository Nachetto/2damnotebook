package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Payment;

import java.util.List;

public interface PaymentsRepository {
    List<Payment> getAll();
    int save(Payment payment);
    void update(Payment payment);
    boolean delete(int paymentId);
    List<Payment> getPaymentsByPatient();
}
