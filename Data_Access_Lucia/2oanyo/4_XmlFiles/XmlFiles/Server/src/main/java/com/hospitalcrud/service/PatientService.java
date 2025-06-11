package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final MedRecordService medRecordService;
    private final PatientDAO dao;
    public PatientService(MedRecordService medRecordService, PatientDAO dao) {
        this.dao = dao;
        this.medRecordService = medRecordService;
    }

    public List<PatientUI> getPatients() {
        return dao.getAll().stream().map(p -> p.toPatientUI()).collect(Collectors.toList());
    }

    public int addPatient(PatientUI patientUI) {
        return dao.save(patientUI.toPatient());
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient());
    }

    public boolean delete(int patientId, boolean confirm) {
        if (!medRecordService.checkPatientMedRecords(patientId))
            throw new MedicalRecordException("Patient has medical records");
        return dao.delete(patientId, confirm);
    }

    public List<MedRecordUI> getPatientMedRecords(int patientId) {
        return medRecordService.getMedRecords(patientId);
    }
}
