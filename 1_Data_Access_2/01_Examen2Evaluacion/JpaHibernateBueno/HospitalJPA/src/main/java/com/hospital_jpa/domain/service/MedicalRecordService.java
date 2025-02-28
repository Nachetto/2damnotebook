package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.interfaces.MedicalRecordsRepository;
import com.hospital_jpa.dao.interfaces.MedicationsRepository;
import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.dao.model.Medication;
import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.domain.model.MedicalRecordUI;
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
        return medicalRecordsRepository.save(new MedicalRecord(new Patient(medicalRecordUI.getIdPatient()), medicalRecordUI.getIdDoctor(),
                medicalRecordUI.getDescription(), LocalDate.parse(medicalRecordUI.getDate()),parseMedications(medicalRecordUI.getMedications())));

    }

    private List<Medication> parseMedications(List<String> medications) {
        List<Medication> medicationList = new ArrayList<>();
        medications.forEach(medication -> medicationList.add(new Medication(medication,"every 8 hours")));
        return medicationList;
    }

    public List<MedicalRecordUI> getMedicalRecords(int idPatient) {
        List<MedicalRecordUI> medicalRecordsUI = new ArrayList<>();
        medicalRecordsRepository.getAll(idPatient).forEach(mr ->
                medicalRecordsUI.add(new MedicalRecordUI(mr.getId(), mr.getDiagnosis(),
                        mr.getDate().toString(),
                        mr.getId(), mr.getIdDoctor(),
                        parseStringMedications(mr.getMedications()))));
        return medicalRecordsUI;
    }

    private List<String> parseStringMedications(List<Medication> medications) {
        List<String> stringMedications = new ArrayList<>();
        medications.forEach(m -> stringMedications.add(m.getMedicationName()));
        return stringMedications;
    }

    public void deleteMedicalRecord(int id) {
        medicalRecordsRepository.delete(new MedicalRecord(id));
    }

    public void updateMedicalRecord(MedicalRecordUI medicalRecordUI) {
        medicalRecordsRepository.update(new MedicalRecord(medicalRecordUI.getId(),new Patient(medicalRecordUI.getIdPatient()), medicalRecordUI.getIdDoctor(),
                medicalRecordUI.getDescription(), LocalDate.parse(medicalRecordUI.getDate()),parseMedicationsWithRecordId(medicalRecordUI,medicalRecordUI.getMedications())));
    }

    private List<Medication> parseMedicationsWithRecordId(MedicalRecordUI medicalRecordUI, List<String> medications) {
        List<Medication> medicationList = new ArrayList<>();
        medications.forEach(medication -> medicationList.add(new Medication(new MedicalRecord(medicalRecordUI.getId()),medication,"every 8 hours")));
        return medicationList;
    }
}
