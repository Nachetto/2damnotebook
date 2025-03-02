package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.MedicalRecord;

import java.util.List;


public interface MedicalRecordsRepository {
    List<MedicalRecord> getAll(int idPatient);
    void delete(MedicalRecord medicalRecord);
    int save(MedicalRecord medicalRecord);
    void update(MedicalRecord medicalRecord);
}
