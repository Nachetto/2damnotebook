package com.hospitalcrud.dao.model;

import com.hospitalcrud.dao.repository.MedicationDAO;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.service.MedicationService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MedRecord {

    private int id;
    private int idPatient;
    private int idDoctor;
    private String diagnosis;
    private LocalDate date;

    public MedRecord(int id, int idPatient, int idDoctor, String description, LocalDate datee) {
        this.id = id;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.diagnosis = description;
        this.date = datee;
    }

    public MedRecordUI toMedRecordUI(MedicationService medicationService) {
        return new MedRecordUI(id, idPatient, idDoctor, diagnosis, date.toString(), medicationService.getMedications(id));
    }
}