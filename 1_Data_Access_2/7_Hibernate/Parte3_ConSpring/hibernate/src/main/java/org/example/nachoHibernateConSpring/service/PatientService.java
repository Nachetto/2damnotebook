package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Patient;
import org.example.nachoHibernateConSpring.dao.repository.CredentialDAO;
import org.example.nachoHibernateConSpring.dao.repository.PatientDAO;
import org.example.nachoHibernateConSpring.domain.error.MedicalRecordException;
import org.example.nachoHibernateConSpring.domain.error.UsernameDuplicatedException;
import org.example.nachoHibernateConSpring.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientDAO dao;
    private final CredentialDAO credentialDAO;

    public PatientService(PatientDAO dao, CredentialDAO credentialDAO) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
    }

    public List<PatientUI> getPatients() {
        return dao.findAll()
                .stream()
                .map(Patient::toPatientUI).toList();
    }

    public int addPatient(PatientUI patientUI) {
        // Check for duplicated username
        if (credentialDAO.findByUsername(patientUI.getUserName()).size() > 0) {
            throw new UsernameDuplicatedException("Username duplicated, it already exists");
        }

        Patient savedPatient = dao.save(patientUI.toPatient());

        if (savedPatient.getId() == 0) {
            throw new MedicalRecordException("Unexpected error saving patient, no patient was saved, rolling back...");
        }
        return savedPatient.getId();
    }

    public void updatePatient(PatientUI patientUI) {
        dao.save(patientUI.toPatient());
    }

    public boolean delete(int patientId, boolean confirm) {
        // This way of making it makes it so that it will always call all those deletes even after checking if there are records, but it is ok, it is a small amount of data
//        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
//                throw new MedicalRecordException("Patient has medical records, cannot delete.");
//        }
        dao.deleteById(patientId);
        return true;
    }

    public Patient getById(int id) {
        return dao.findById(id).orElse(null);
    }
}