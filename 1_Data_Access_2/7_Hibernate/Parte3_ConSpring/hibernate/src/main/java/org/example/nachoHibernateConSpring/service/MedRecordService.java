package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.repository.MedRecordDAO;
import org.example.nachoHibernateConSpring.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedRecordService {
    private final MedRecordDAO dao;
    private final MedicationService medicationService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedRecordService(MedRecordDAO dao, MedicationService medicationService, PatientService patientService, DoctorService doctorService) {
        this.dao = dao;
        this.medicationService = medicationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public int add(MedRecordUI medRecordUI) {
        MedRecord medRecord = medRecordUI.toMedRecord(patientService, doctorService);
        MedRecord savedMedRecord = dao.save(medRecord);
        return savedMedRecord.getId(); // Assuming getId() returns the ID of the saved MedRecord
    }

    public void update(MedRecordUI medRecordUI) {
        MedRecord medRecord = medRecordUI.toMedRecord(patientService, doctorService);
        dao.save(medRecord); // Use save for both insert and update
    }

    public void delete(int medRecordId) {
        medicationService.delete(medRecordId);
        dao.deleteById(medRecordId);
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        return dao.findByPatientId(patientId).stream().map(MedRecord::toMedRecordUI).toList();
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.findByPatientId(patientId).isEmpty();
    }
}