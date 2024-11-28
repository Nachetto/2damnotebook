package org.example.domain;

import lombok.Data;

@Data
public class Payment {
    private int paymentID;
    private double amount;
    private String date;
    private int medicationID;
}
