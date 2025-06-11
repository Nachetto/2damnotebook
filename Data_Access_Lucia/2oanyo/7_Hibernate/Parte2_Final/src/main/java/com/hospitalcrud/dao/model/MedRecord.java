package com.hospitalcrud.dao.model;

import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.service.MedicationService;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "medical_records")
@NamedQueries({
        @NamedQuery(name = "MedRecord.getAll", query = "FROM MedRecord"),
        @NamedQuery(name = "MedRecord.get", query = "FROM MedRecord WHERE patient.id = :patientId")
})
public class MedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column
    private String diagnosis;

    @Column(name = "admission_date")
    private LocalDate date;

    @OneToMany(mappedBy = "medRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications;

    public MedRecordUI toMedRecordUI() {
        List<String> medicationNames = medications.stream()
                .map(Medication::getMedicationName)
                .toList();
        return new MedRecordUI(id, patient.getId(), doctor.getId(), diagnosis, date.toString(), medicationNames);
    }

}