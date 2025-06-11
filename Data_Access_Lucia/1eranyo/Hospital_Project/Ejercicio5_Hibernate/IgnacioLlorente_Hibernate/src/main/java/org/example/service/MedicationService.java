package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.MedicationDaoImpl;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;

import java.util.List;

public class MedicationService {
    private final MedicationDaoImpl medicationDao;
    @Inject
    public MedicationService(MedicationDaoImpl medicationDao) {
        this.medicationDao = medicationDao;
    }

    public Either<String, List<PrescribedMedication>> getAll() {
        return medicationDao.getAll();
    }

    public Either<String, PrescribedMedication> get(int id) {
        return medicationDao.get(id);
    }

    public int save(PrescribedMedication m) {
        return medicationDao.save(m);
    }

    public int modify(PrescribedMedication modifiedmedication) {
        return medicationDao.modify(modifiedmedication);
    }

    public int delete(PrescribedMedication m) {
        return medicationDao.delete(m);
    }

    public int deleteByPatient(int id) {
        return medicationDao.deleteByPatient(id);
    }

    public List<Patient> getPatientsMedicatedWith(String medicationName) {
        return medicationDao.getPatientsMedicatedWith(medicationName);
    }

    public int getNewMedicationID() {
        return medicationDao.getNewMedicationID();
    }

}
