package com.hospitalcrud.dao.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Payment {
    private int patientId;
    private int amount;
    private LocalDate date;

    public Payment(int patientId, int amount) {
        this.patientId = patientId;
        this.amount = amount;
    }

    public Payment(int patientId, float amount, LocalDate paymentDate) {
        this.patientId = patientId;
        this.amount = (int) amount;
        this.date = paymentDate;
    }
}
