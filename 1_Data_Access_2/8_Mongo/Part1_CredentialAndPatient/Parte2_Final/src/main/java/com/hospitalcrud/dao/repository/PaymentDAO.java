package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDAO {
    List<Payment> getAll();
}
