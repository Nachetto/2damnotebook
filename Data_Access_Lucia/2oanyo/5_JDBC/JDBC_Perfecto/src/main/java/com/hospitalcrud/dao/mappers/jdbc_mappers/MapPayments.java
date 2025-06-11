package com.hospitalcrud.dao.mappers.jdbc_mappers;

import com.hospitalcrud.dao.model.Payment;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapPayments {
    public List<Payment> readRS(ResultSet paid) {
        List<Payment> payments = new ArrayList<>();
        try {
            while (paid.next()) {
                payments.add(new Payment(
                        paid.getInt("patient_id"),
                        paid.getFloat("amount"),
                        paid.getDate("payment_date").toLocalDate()));
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Payment> mapPayments(ResultSet paid) {
        List<Payment> payments = new ArrayList<>();
        try {
            while (paid.next()) payments.add(new Payment(
                    paid.getInt(2),
                    (int)paid.getFloat(1))
            );
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
