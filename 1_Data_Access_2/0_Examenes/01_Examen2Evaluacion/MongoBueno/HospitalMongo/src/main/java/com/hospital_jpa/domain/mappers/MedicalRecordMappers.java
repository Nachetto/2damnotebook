package com.hospital_jpa.domain.mappers;

import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.domain.model.MedicalRecordUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMappers {

    public MedicalRecordUI toMedicalRecordUI (MedicalRecord medicalRecord, int id, int patientId, int doctorId) {
        return new MedicalRecordUI(
                id,
                medicalRecord.getDiagnosis(),
                medicalRecord.getDate().toString(),
                patientId,
                doctorId,
                medicalRecord.getMedications()
                );
    }

    public MedicalRecord toMedicalRecord (MedicalRecordUI medicalRecord,ObjectId medicalRecordId, ObjectId patient, ObjectId doctor) {
        return new MedicalRecord(
                medicalRecordId,
                medicalRecord.getDate(),
                medicalRecord.getDiagnosis(),
                doctor,
                medicalRecord.getMedications(),
                patient
        );
    }

    public MedicalRecord toMedicalRecord (MedicalRecordUI medicalRecord, ObjectId patient, ObjectId doctor) {
        return new MedicalRecord(
                medicalRecord.getDate(),
                medicalRecord.getDiagnosis(),
                doctor,
                medicalRecord.getMedications(),
                patient
        );
    }
}
