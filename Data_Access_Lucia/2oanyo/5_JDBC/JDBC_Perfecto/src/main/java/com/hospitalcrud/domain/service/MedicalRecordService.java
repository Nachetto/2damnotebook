package com.hospitalcrud.domain.service;


import com.hospitalcrud.dao.model.MedicalRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.respositories.MedicalRecordsRepository;
import com.hospitalcrud.dao.respositories.MedicationsRepository;
import com.hospitalcrud.domain.model.MedicalRecordUI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final MedicationsRepository medicationsRepository;

    public MedicalRecordService(MedicalRecordsRepository medicalRecordsRepository, MedicationsRepository medicationsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.medicationsRepository = medicationsRepository;
    }

    public int addMedicalRecord(MedicalRecordUI medicalRecordUI) {
        return medicalRecordsRepository.save(new MedicalRecord(medicalRecordUI.getId(),
                medicalRecordUI.getIdPatient(), medicalRecordUI.getIdDoctor(),
                medicalRecordUI.getDescription(), LocalDate.parse(medicalRecordUI.getDate()),parseMedications(medicalRecordUI.getMedications(),medicalRecordUI.getId())));

    }

    private List<Medication> parseMedications(List<String> medications, int medicalRecordId) {
        List<Medication> medicationList = new ArrayList<>();
        medications.forEach(medication -> medicationList.add(new Medication(1,medication,medicalRecordId,"every 8 hours")));
        return medicationList;
    }

    public List<MedicalRecordUI> getMedicalRecords(int idPatient) {
        List<MedicalRecordUI> medicalRecordsUI = new ArrayList<>();
        medicalRecordsRepository.getAll(idPatient).forEach(mr ->
                medicalRecordsUI.add(new MedicalRecordUI(mr.getId(), mr.getDiagnosis(),
                        mr.getDate().toString(),
                        mr.getIdPatient(), mr.getIdDoctor(),
                        parseStringMedications(medicationsRepository.getPrescribedMedications(mr.getId())))));
        return medicalRecordsUI;
    }

    private List<String> parseStringMedications(List<Medication> medications) {
        List<String> stringMedications = new ArrayList<>();
        medications.forEach(m -> stringMedications.add(m.getMedicationName()));
        return stringMedications;
    }

    public void deleteMedicalRecord(int id) {
        medicalRecordsRepository.delete(new MedicalRecord(
                id,-1,-1,null,null));
    }

    public void updateMedicalRecord(MedicalRecordUI medicalRecordUI) {
        medicalRecordsRepository.update(new MedicalRecord(medicalRecordUI.getId(), medicalRecordUI.getIdPatient(),
                medicalRecordUI.getIdDoctor(), medicalRecordUI.getDescription(), LocalDate.parse(medicalRecordUI.getDate()),
                parseMedications(medicalRecordUI.getMedications(),medicalRecordUI.getId())));
    }
}
