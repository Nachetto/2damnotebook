package org.example.nachoHibernateConSpring.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "prescribed_medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    private MedRecord medRecord;

    @Column(name = "medication_name", nullable = false)
    private String medicationName;

    @Column(name = "dosage")
    private String dosage;

    public Medication(String medicationName, MedRecord medRecord) {
        this.medicationName = medicationName;
        this.medRecord = medRecord;
    }
}




