package com.hospital_jpa.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "patient_payments")
@NamedQueries({
        @NamedQuery(name = "getPaymentsByPatient", query = "select new Payment(p.patient.id,sum(p.amount)) from Payment p group by p.patient.id"),
        @NamedQuery(name = "deletePayments", query = "delete from Payment pay where pay.patient.id = :patient_id")
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;
    @Column
    private int amount;
    @Column(name = "payment_date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Payment(int id, long amount) {
        this.patient = new Patient(id);
        this.amount = (int) amount;
    }
}