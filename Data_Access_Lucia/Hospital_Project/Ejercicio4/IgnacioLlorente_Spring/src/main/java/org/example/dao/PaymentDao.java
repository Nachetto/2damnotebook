package org.example.dao;

import io.vavr.control.Either;
import org.example.domain.Payment;

import java.util.List;

public interface PaymentDao {

    Either<String, List<Payment>> getAll();

    int save(Payment c);

    int modify(Payment c, Payment cu);

    int delete(Payment c);
}
