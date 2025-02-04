package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Patient;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.example.nachoHibernateConSpring.dao.repository.*;
import org.example.nachoHibernateConSpring.domain.error.BadRequestException;
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
    private final SessionDao sessionDao;

    public PatientService(PatientDAO dao, CredentialDAO credentialDAO, MedRecordDAO medRecordDAO, MedicationDAO medicationDAO, PaymentDAO paymentDAO, AppointmentDao appointmentDao, SessionDao sessionDao) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
        this.medRecordDAO = medRecordDAO;
        this.medicationDAO = medicationDAO;
        this.paymentDAO = paymentDAO;
        this.appointmentDao = appointmentDao;
        this.sessionDao = sessionDao;
    }

    private void checkAdminOrDoctorPermission() {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (!session.getUserType().equals("admin") && !session.getUserType().equals("doctor")) {
            throw new BadRequestException("Only admin or doctor can perform this action");
        }
    }

    private void checkPatientPermission(int patientId) {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (session.getUserType().equals("patient") && session.getUserId() != patientId) {
            throw new BadRequestException("Patient can only access their own data");
        }
    }



    public List<PatientUI> getPatients() {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        List<Patient> patients;
        if (session.getUserType().equals("doctor")) {
            // Doctor
            patients = dao.findAll();
        } else if (session.getUserType().equals("patient")) {
            // patient
            patients = List.of(dao.findById(session.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found")));
        } else { // admin
            patients = dao.findAll();
        }
        return patients.stream().map(Patient::toPatientUI).toList();
    }

    @Transactional
    public int addPatient(PatientUI patientUI) {
        checkAdminOrDoctorPermission(); // only admin/doctor
        try {
            if (!credentialDAO.findByUsername(patientUI.getUserName()).isEmpty()) {
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
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException(" session not found");
        if (session.getUserType().equals("patient")) {
            checkPatientPermission(patientUI.getId()); // no need to do this, but just in case :D
        }
        Patient existingPatient = dao.findById(patientUI.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        existingPatient.setName(patientUI.getName());
        existingPatient.setBirthDate(patientUI.getBirthDate());
        existingPatient.setPhone(patientUI.getPhone());

        dao.save(existingPatient);
    }

    @Transactional
    public boolean delete(int patientId, boolean confirm) {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (session.getUserType().equals("doctor")) {
            throw new BadRequestException("Doctor cannot delete patients");
        }
        if (session.getUserType().equals("patient") ) {
            throw new BadRequestException("Patient cannot delete himself");
        }
        
        
        Patient patient = dao.getById(patientId);
        List<MedRecord> medRecords = medRecordDAO.findByPatientId(patientId);
        if (!confirm && !medRecords.isEmpty()) {
            throw new MedicalRecordException("Patient has medical records, cannot delete.");
        } else {
            try {
                //  medications and medical records
                medRecords.forEach(medRecord -> {
                    medicationDAO.deleteByMedRecord(medRecord);
                    medRecordDAO.deleteById(medRecord.getId());
                });

                //  payments
                paymentDAO.deleteByPatientId(patientId);

                //  appointments
                appointmentDao.deleteByPatientId(patientId);

                //  credentials
                credentialDAO.deleteByPatient(patient);

                //  patient
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