package com.hospital_jpa.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prescribed_medications")
@NamedQuery(name = "getAll", query = "select t1 from Medication t1 join (select t2.medicationName as medicationName, min(t2.id) as firstId from Medication t2 group by t2.medicationName) subquery on t1.medicationName = subquery.medicationName and t1.id = subquery.firstId")

public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private int id;
    @Column(name = "medication_name")
    private String medicationName;
    @ManyToOne
    @JoinColumn(name = "record_id")
    private MedicalRecord medicalRecord;
    @Column
    private String dosage;


    public Medication(int id, String medicationName) {
        this.id = id;
        this.medicationName = medicationName;
        this.dosage = "";
    }
    public Medication(String medicationName, String dosage) {
        this.medicationName = medicationName;
        this.dosage = dosage;
    }
    public Medication(MedicalRecord medicalRecord, String medicationName, String dosage) {
        this.medicalRecord = medicalRecord;
        this.medicationName = medicationName;
        this.dosage = dosage;
    }
}