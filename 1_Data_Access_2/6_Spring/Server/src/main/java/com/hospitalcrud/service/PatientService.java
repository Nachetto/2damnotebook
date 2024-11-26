package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.spring.CredentialRepository;
import com.hospitalcrud.dao.repository.spring.PatientRepository;
import com.hospitalcrud.domain.error.ConflictException;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return dao.getAll().stream().map(p -> p.toPatientUI()).collect(Collectors.toList());
    }

    public int addPatient(PatientUI patientUI) {
        //check for duplicated username
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new ConflictException("Username already exists");

        if (dao.save(patientUI.toPatient())==1)
            return credentialDAO.save(patientUI.toCredential(dao.getPatientId(patientUI.getName())));
        return 0;
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
