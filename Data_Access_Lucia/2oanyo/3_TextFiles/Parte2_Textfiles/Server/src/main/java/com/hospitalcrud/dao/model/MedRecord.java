package com.hospitalcrud.dao.model;

import com.hospitalcrud.domain.model.MedRecordUI;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedRecord {
    private int id;
    private int idPatient;
    private int idDoctor;
    private String diagnosis;
    private LocalDate date;
    private List<Medication> medications;


    public MedRecordUI toMedRecordUI() {
        return new MedRecordUI(id, idPatient, idDoctor, diagnosis, date.toString(),
                medications.stream().map(m -> m.getMedicationName()).toList());
    }
}

