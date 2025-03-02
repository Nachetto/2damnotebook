package com.hospital_jpa.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medical_records")
@NamedQueries({
        @NamedQuery(name = "getPatientMedicalRecords", query = "from MedicalRecord m where m.patient.id = :id"),
        @NamedQuery(name = "deletePatientMedicalRecords", query = "delete from MedicalRecord m where m.patient.id = :patient_id")
})
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Column(name = "doctor_id")
    private int idDoctor;
    @Column
    private String diagnosis;
    @Column(name = "admission_date")
    private LocalDate date;
    @OneToMany(mappedBy = "medicalRecord", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Medication> medications;

    public MedicalRecord(Patient patient, int idDoctor, String diagnosis, LocalDate date, List<Medication> medications) {
        this.patient = patient;
        this.idDoctor = idDoctor;
        this.diagnosis = diagnosis;
        this.date = date;
        this.medications = medications;
    }

    public MedicalRecord (int id) {
        this.id = id;
    }
}
