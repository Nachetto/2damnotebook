package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.PatientDaoImpl;
import org.example.dao.impl.RecordDaoImpl;
import org.example.domain.Credential;
import org.example.domain.Patient;
import org.example.domain.Record;

import java.util.List;

public class PatientService {
    private PatientDaoImpl patientDao;
    private RecordDaoImpl recordDao;

    @Inject
    public PatientService(PatientDaoImpl patientDao, RecordDaoImpl recordDao) {
        this.patientDao = patientDao;
        this.recordDao = recordDao;
    }

    public Either<String, Integer> getTotalAmmountPayed(int id) {
        return patientDao.getTotalAmmountPayed(id);
    }

    public Either<String, List<Patient>> getAllPatientsWithTotalAmmountPaid() {
        return patientDao.getAllPatientsWithTotalAmmountPaid();
    }

    public Either<String, List<Patient>> getAll() {
        return patientDao.getAll();
    }

    public Either<String, Patient> get(int id) {
        return patientDao.get(id);
    }

    public boolean checkLogin(Credential p) {
        return patientDao.checkLogin(p);
    }

    public int save(Patient p) {
        return patientDao.save(p);
    }

    public int modify(Patient initialpatient, Patient modifiedpatient) {
        return patientDao.modify(initialpatient, modifiedpatient);
    }

    public Either<String, Boolean> isPatientType(String username) {
        return patientDao.isPatientType(username);
    }

    public int delete(int p, boolean deepDeletion) {
        return patientDao.delete(p, deepDeletion);
    }

    public List<Record> getRecords(int patientID) {
        return recordDao.getAll().get().stream().filter(r -> r.getPatientID() == patientID).toList();
    }

    public Either<String, Boolean> isPatient(String usuario) {
        return patientDao.isPatientType(usuario);
    }
}
