package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.dao.model.Medication;

import java.util.List;

public interface MedicationsRepository {
    List<Medication> getPrescribedMedications (int medicalRecordId);
    List<Medication> getAll();
    void deletePatientMedications(int patientId);
    void deleteMedicalRecordMedications(int medicalRecordId);
    void save(MedicalRecord medicalRecord);
    void update(MedicalRecord medicalRecord);
}
