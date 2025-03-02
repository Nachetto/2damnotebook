package com.hospitalcrud.dao.respositories;

import com.hospitalcrud.dao.model.Payment;

import java.util.List;

public interface PaymentsRepository {
    List<Payment> getAll();
    int save(Payment payment);
    void update(Payment payment);
    boolean delete(int paymentId);
    List<Payment> getPaymentsByPatient();
    void deletePatientPayments(int patientId);
}
