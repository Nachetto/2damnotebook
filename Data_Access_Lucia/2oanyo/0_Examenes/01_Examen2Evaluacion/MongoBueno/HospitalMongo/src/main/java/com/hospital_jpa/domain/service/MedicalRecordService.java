package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.common.Constants;
import com.hospital_jpa.dao.interfaces.DoctorsRepository;
import com.hospital_jpa.dao.interfaces.MedicalRecordsRepository;
import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.domain.error.Error_Creating_Object;
import com.hospital_jpa.domain.mappers.MedicalRecordMappers;
import com.hospital_jpa.domain.model.MedicalRecordUI;
import com.hospital_jpa.domain.utils.IdManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final IdManager idManager;
    private final MedicalRecordMappers mappers;
    private final DoctorsRepository doctorsRepository;

    public int addMedicalRecord(MedicalRecordUI medicalRecordUI) {
        ObjectId generatedId = medicalRecordsRepository.save(mappers.toMedicalRecord(
                medicalRecordUI,
                idManager.getPatientObjectId(medicalRecordUI.getIdPatient()),
                idManager.getDoctorObjectId(medicalRecordUI.getIdDoctor())));
        if (generatedId != null)
            idManager.addMedicalRecordId(generatedId);
        else
            throw new Error_Creating_Object(Constants.ERROR_CREATING_MEDICAL_RECORD);
        return idManager.getMedicalRecordAutoIncrement()-1;
    }

    public List<MedicalRecordUI> getAll(int patientId) {
        if (idManager.getDoctorIds().isEmpty())
            idManager.fillDoctorIds(doctorsRepository.getAll());
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.getAll(idManager.getPatientObjectId(patientId));
        idManager.fillMedicalRecordsIds(medicalRecords);
        return medicalRecords
                .stream()
                .map(mr -> mappers.toMedicalRecordUI(
                        mr,
                        idManager.getMedicalRecordIntId(mr.getId()),
                        patientId,
                        idManager.getDoctorIntId(mr.getDoctor())
                        ))
                .toList();
    }

    public void deleteMedicalRecord(int id) {
        medicalRecordsRepository.delete(idManager.getMedicalRecordObjectId(id));
    }

    public void updateMedicalRecord(MedicalRecordUI medicalRecordUI) {
        medicalRecordsRepository.update(mappers.toMedicalRecord(
                medicalRecordUI,
                idManager.getMedicalRecordObjectId(medicalRecordUI.getId()),
                idManager.getPatientObjectId(medicalRecordUI.getIdPatient()),
                idManager.getDoctorObjectId(medicalRecordUI.getIdDoctor())));
    }
}
