package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.spring.CredentialRepository;
import com.hospitalcrud.dao.repository.spring.PatientRepository;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.error.UsernameDuplicatedException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        if (patientId == -1) {
            return 0;
        }

        return credentialDAO.save(patientUI.toCredential(patientId));
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient());
    }

    @Transactional
    public boolean delete(int patientId, boolean confirm) {
        //this way of making it makes it  so that it will always call all those deletes even after checking if there are records, but it is ok, it is a small amount of data
        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
                throw new MedicalRecordException("Patient has medical records, cannot delete.");
        }

        //deleting all the medical records of the patient, and all the medications of those records
        medRecordService.deleteByPatientId(patientId);

        //deleting the credentials of the patient
        if (!credentialService.delete(patientId)) {
            //this  it would mean the patient has no credentials, rarer than a unicorn
            throw new MedicalRecordException("Unexpected error deleting patient credentials, no credentials were deleted, rolling back...");
        }

        //deleting appointments and the final patient
        return dao.delete(patientId, confirm);
    }


    public List<MedRecordUI> getPatientMedRecords(int patientId) {
        return medRecordService.getMedRecords(patientId);
    }
}
