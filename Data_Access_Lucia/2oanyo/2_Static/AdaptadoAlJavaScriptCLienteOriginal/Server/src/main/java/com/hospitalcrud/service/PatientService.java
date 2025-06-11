package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.staticDAO.PatientRepository;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;

import java.util.List;
import java.util.stream.Collectors;

public class PatientService {
    private final MedRecordService medRecordService;
    private final PatientRepository dao;
    //llamar al DAO inyectado
    public PatientService() {
        this.dao = new PatientRepository();
        this.medRecordService = new MedRecordService();
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
        //check if patient has med records and if so throw exception MedicalRecordException
        if (!medRecordService.checkPatientMedRecords(patientId))
            throw new MedicalRecordException("Patient has medical records");
        return dao.delete(patientId, confirm);
    }

    public List<MedRecordUI> getPatientMedRecords(int patientId) {
        return medRecordService.getMedRecords(patientId);
    }
}
