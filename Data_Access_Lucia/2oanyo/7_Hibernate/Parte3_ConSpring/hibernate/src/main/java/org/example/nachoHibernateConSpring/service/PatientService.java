package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Patient;
import org.example.nachoHibernateConSpring.dao.repository.*;
import org.example.nachoHibernateConSpring.domain.error.MedicalRecordException;
import org.example.nachoHibernateConSpring.domain.error.UsernameDuplicatedException;
import org.example.nachoHibernateConSpring.domain.model.PatientUI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final PatientDAO dao;
    private final CredentialDAO credentialDAO;
    private final MedRecordDAO medRecordDAO;
    private final MedicationDAO medicationDAO;
    private final PaymentDAO paymentDAO;
    private final AppointmentDao appointmentDao;

    public PatientService(PatientDAO dao, CredentialDAO credentialDAO, MedRecordDAO medRecordDAO, MedicationDAO medicationDAO, PaymentDAO paymentDAO, AppointmentDao appointmentDao) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
        this.medRecordDAO = medRecordDAO;
        this.medicationDAO = medicationDAO;
        this.paymentDAO = paymentDAO;
        this.appointmentDao = appointmentDao;
    }

    public List<PatientUI> getPatients() {
        return dao.findAll()
                .stream()
                .map(Patient::toPatientUI).toList();
    }

    @Transactional
    public int addPatient(PatientUI patientUI) {
        try {
            if (credentialDAO.findByUsername(patientUI.getUserName()).size() > 0) {
                throw new UsernameDuplicatedException("Username duplicated, it already exists");
            }

            Credential credential = new Credential();
            credential.setUsername(patientUI.getUserName());
            credential.setPassword(patientUI.getPassword());

            Patient patient = patientUI.toPatient();
            patient.setCredential(credential);
            credential.setPatient(patient);

            Patient savedPatient = dao.save(patient);

            if (savedPatient.getId() == 0) {
                throw new MedicalRecordException("Unexpected error saving patient, no patient was saved, rolling back...");
            }
            return savedPatient.getId();
        } catch (DataIntegrityViolationException e) {
            throw new MedicalRecordException("Data integrity violation while saving patient "+ e);
        } catch (Exception e) {
            throw new MedicalRecordException("Unexpected error while saving patient "+ e);
        }
    }

    public void updatePatient(PatientUI patientUI) {
        Patient existingPatient = dao.findById(patientUI.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        existingPatient.setName(patientUI.getName());
        existingPatient.setBirthDate(patientUI.getBirthDate());
        existingPatient.setPhone(patientUI.getPhone());

        dao.save(existingPatient);
    }

    @Transactional
    public boolean delete(int patientId, boolean confirm) {
        Patient patient = dao.getById(patientId);
        List<MedRecord> medRecords = medRecordDAO.findByPatientId(patientId);
        if (!confirm && !medRecords.isEmpty()) {
            throw new MedicalRecordException("Patient has medical records, cannot delete.");
        } else {
            try {
                // Delete medications and medical records
                medRecords.forEach(medRecord -> {
                    medicationDAO.deleteByMedRecord(medRecord);
                    medRecordDAO.deleteById(medRecord.getId());
                });

                // Delete payments
                paymentDAO.deleteByPatientId(patientId);

                // Delete appointments
                appointmentDao.deleteByPatientId(patientId);

                // Delete credentials
                credentialDAO.deleteByPatient(patient);

                // Delete patient
                dao.deleteById(patientId);
            } catch (DataIntegrityViolationException e) {
                throw new MedicalRecordException("Error deleting patient data, rolling back...");
            }
        }
        return true;
    }

    public Patient getById(int id) {
        return dao.findById(id).orElse(null);
    }
}