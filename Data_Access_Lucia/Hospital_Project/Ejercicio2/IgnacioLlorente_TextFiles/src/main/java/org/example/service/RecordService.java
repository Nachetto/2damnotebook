package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.RecordDaoImpl;
import org.example.domain.Doctor;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.Record;

import java.util.List;

public class RecordService {
    private final RecordDaoImpl recordDao;
    @Inject
    public RecordService(RecordDaoImpl recordDao) {
        this.recordDao = recordDao;
    }

    public Either<String, List<Record>> getAll() {
        return recordDao.getAll();
    }

    public Either<String, Record> get(int id) {
        return recordDao.get(id);
    }

    public int save(Record r) {
        return recordDao.save(r);
    }

    public int modify(Record initialrecord, Record modifiedrecord) {
        return recordDao.modify(initialrecord, modifiedrecord);
    }

    public int delete(Record r) {
        return recordDao.delete(r);
    }

    public int deleteByPatient(int id) {
        return recordDao.deleteByPatient(id);
    }

    public boolean hasMedications(int id, MedicationService medicationService) {
        return recordDao.hasMedications(id,  medicationService);
    }

    public int getNewRecordID() {
        return recordDao.getNewRecordID();
    }

    public List<Integer> getRecordIdsFromPatientId(int patientId) {
        return recordDao.getRecordIdsFromPatientId(patientId);
    }

    public int saveToXML(List<Record> records, List<PrescribedMedication> medications, List<Patient> patients, List<Doctor> doctors) {
        return recordDao.saveToXML(records, medications, patients, doctors);
    }

    public List<PrescribedMedication> medicationsFromAPatientXML(int patientID) {
        return recordDao.medicationsFromAPatientXML(patientID);
    }
}
