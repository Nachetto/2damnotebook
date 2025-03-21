package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.hibernate.MedRecordRepository;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedRecordService {
    private final MedRecordRepository dao;
    private final MedicationService medicationService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedRecordService(MedRecordRepository dao, MedicationService medicationService, PatientService patientService, DoctorService doctorService) {
        this.dao = dao;
        this.medicationService = medicationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public int add(MedRecordUI medRecordUI) {
        //add medications as well if everything is ok
        return  dao.save(medRecordUI.toMedRecord(patientService, doctorService));





    }

    public void update(MedRecordUI medRecordUI) {
        dao.update(medRecordUI.toMedRecord(patientService, doctorService));
    }

    public void delete(int medRecordId) {
        medicationService.delete(medRecordId);
        if (!dao.delete(medRecordId)) {
            throw new InternalServerErrorException("Error deleting med record");
        }
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        return dao.get(patientId).stream().map(m -> m.toMedRecordUI()).toList();
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.get(patientId).isEmpty();
    }
}