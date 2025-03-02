package com.hospitalcrud.dao.respositories;

import com.hospitalcrud.dao.model.MedicalRecord;
import com.hospitalcrud.dao.model.Medication;

import java.util.List;

public interface MedicationsRepository {
    List<Medication> getPrescribedMedications (int medicalRecordId);
    List<Medication> getAll();
    void deletePatientMedications(int patientId);
    void deleteMedicalRecordMedications(int medicalRecordId);
    void save(MedicalRecord medicalRecord);
    void update(MedicalRecord medicalRecord);
}
