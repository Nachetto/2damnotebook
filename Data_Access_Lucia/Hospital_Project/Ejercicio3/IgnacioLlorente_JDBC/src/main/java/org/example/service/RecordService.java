package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.MedicationDaoImpl;
import org.example.dao.impl.RecordDaoImpl;
import org.example.domain.Doctor;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.Record;

import java.util.List;

public class RecordService {
    private final RecordDaoImpl recordDao;
    private final MedicationDaoImpl medicationDao;
    @Inject
    public RecordService(RecordDaoImpl recordDao, MedicationDaoImpl medicationDao) {
        this.recordDao = recordDao;
        this.medicationDao = medicationDao;
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

    public int save(Record r, PrescribedMedication medication1, PrescribedMedication medication2) {
        int recordID=r.getRecordID();
        medication1.setRecordID(recordID);
        medication2.setRecordID(recordID);
        if (medicationDao.save(medication1) == -1 || medicationDao.save(medication2) == -1)
            return -2;
        return recordDao.save(r); //this returns an int, not the object itself
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

    public int appendMedicationToPatientXML(int patientID, PrescribedMedication medication) {
        return recordDao.appendMedicationToPatientXML(patientID, medication);
    }

    public int deletePatientXML(int id) {
        return recordDao.deletePatientXML(id);
    }

    public int appendRecordXML(int patientID, String diagnosis, String doctorName) {
        return recordDao.appendRecordXML(patientID, diagnosis, doctorName);
    }

    public Either<String, List<Record>> getRecords(int patientId) {
        return recordDao.getRecords(patientId);
    }

    public String medicationsFromARecordId(int recordID) {
        return recordDao.medicationsFromARecordId(recordID);
    }
}
