package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.spring.CredentialRepository;
import com.hospitalcrud.dao.repository.spring.PatientRepository;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.error.UsernameDuplicatedException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final MedRecordService medRecordService;
    private final PatientRepository dao;
    private  final CredentialRepository credentialDAO;

    public PatientService(MedRecordService medRecordService, PatientRepository dao, CredentialRepository credentialDAO) {
        this.dao = dao;
        this.medRecordService = medRecordService;
        this.credentialDAO = credentialDAO;
    }

    public List<PatientUI> getPatients() {
        return dao.getAll().stream().map(Patient::toPatientUI).toList();
    }

    public int addPatient(PatientUI patientUI) {
        //check for duplicated username
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new UsernameDuplicatedException("Username duplicated, it already exists");

        int patientId = dao.save(patientUI.toPatientWithCredentials());

        return patientId;
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient());
    }

    public boolean delete(int patientId, boolean confirm) {
        //here it will always call all those medical record, deletes even after checking if there are records, but it is ok, it is a small amount of data
        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
                throw new MedicalRecordException("Patient has medical records, cannot delete.");
        }

        return dao.delete(patientId, confirm);
    }


    public List<MedRecordUI> getPatientMedRecords(int patientId) {
        return medRecordService.getMedRecords(patientId);
    }
}
