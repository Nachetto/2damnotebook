package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.MedicalRecord;
import org.bson.types.ObjectId;

import java.util.List;


public interface MedicalRecordsRepository {
    List<MedicalRecord> getAll(ObjectId patientId);
    int delete(ObjectId medicalRecordId);
    ObjectId save(MedicalRecord medicalRecord);
    void update(MedicalRecord medicalRecord);
}
