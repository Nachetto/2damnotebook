package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.hibernate.CredentialRepository;
import com.hospitalcrud.dao.repository.hibernate.PatientRepository;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.error.NotFoundException;
import com.hospitalcrud.domain.error.UsernameDuplicatedException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final MedRecordService medRecordService;
    private final PatientRepository dao;
    private  final CredentialRepository credentialDAO;
    private final CredentialService credentialService;

    public PatientService(MedRecordService medRecordService, PatientRepository dao, CredentialRepository credentialDAO, CredentialService credentialService) {
        this.dao = dao;
        this.medRecordService = medRecordService;
        this.credentialDAO = credentialDAO;
        this.credentialService = credentialService;
    }

    public List<PatientUI> getPatients() {
        return dao.getAll().stream().map(Patient::toPatientUI).toList();
    }

    public int addPatient(PatientUI patientUI) {
        //check for duplicated username
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new UsernameDuplicatedException("Username duplicated, it already exists");

        int patientId = dao.save(patientUI.toPatient());

        if (patientId!=1) {
            throw new MedicalRecordException("Unexpected error saving patient, no pawtient was saved, rolling back...");
        }
        return patientId;
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient());
    }

    public boolean delete(int patientId, boolean confirm) {
        //this way of making it makes it  so that it will always call all those deletes even after checking if there are records, but it is ok, it is a small amount of data
        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
                throw new MedicalRecordException("Patient has medical records, cannot delete.");
        }
        return dao.delete(patientId, confirm);
    }

    public Patient getById(int id) {
        return dao.getById(id);
    }

    public List<MedRecordUI> getPatientMedRecords(int patientId) {
        return medRecordService.getMedRecords(patientId);
    }
}
