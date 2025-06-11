package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient_payments")
public class Payment {
    @SerializedName("_id")
    private int paymentId;
    private Patient patient;
    private float amount;
    private LocalDate date;

}
