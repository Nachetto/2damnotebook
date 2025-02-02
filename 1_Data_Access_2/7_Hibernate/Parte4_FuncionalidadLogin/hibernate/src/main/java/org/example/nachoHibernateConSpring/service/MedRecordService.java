package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Medication;
import org.example.nachoHibernateConSpring.dao.repository.MedRecordDAO;
import org.example.nachoHibernateConSpring.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public int add(MedRecordUI medRecordUI) {
        MedRecord medRecord = medRecordUI.toMedRecord(patientService, doctorService);

        // Create and associate Medication entities
        List<Medication> medications = medRecordUI.getMedications().stream()
                .map(medicationName -> new Medication(medicationName, medRecord))
                .collect(Collectors.toList());
        medRecord.setMedications(medications);

        MedRecord savedMedRecord = dao.save(medRecord);
        return savedMedRecord.getId();
    }

    @Transactional
    public void update(MedRecordUI medRecordUI) {
        MedRecord existingMedRecord = dao.findById(medRecordUI.getId())
                .orElseThrow(() -> new IllegalArgumentException("MedRecord not found"));

        existingMedRecord.setDiagnosis(medRecordUI.getDescription());
        existingMedRecord.setDate(LocalDate.parse(medRecordUI.getDate()));

        // Update Medications
        existingMedRecord.getMedications().clear();
        List<Medication> medications = medRecordUI.getMedications().stream()
                .map(medicationName -> new Medication(medicationName, existingMedRecord))
                .collect(Collectors.toList());
        existingMedRecord.getMedications().addAll(medications);

        dao.save(existingMedRecord);
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