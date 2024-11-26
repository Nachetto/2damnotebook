package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.dao.repository.spring.MedRecordRepository;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedRecordService {
    private final MedRecordRepository dao;
    private final MedicationService medicationService;

    public MedRecordService(MedRecordRepository dao, MedicationService medicationService) {
        this.dao = dao;
        this.medicationService = medicationService;
    }

    public int add(MedRecordUI medRecordUI) {
        List<String> medications = medRecordUI.getMedications();
        int result = dao.save(medRecordUI.toMedRecord());
        //add medications as well if everything is ok
        if (result == 1)
            return medicationService.add(medications, medRecordUI.getId());
        return 0;
    }

    public void update(MedRecordUI medRecordUI) {
        dao.update(medRecordUI.toMedRecord());
        //update medications as well, everything is ok at this point, there would hav been an exception if not
        medicationService.update(medRecordUI.getMedications(), medRecordUI.getId());
    }

    public void delete(int medRecordId) {
        medicationService.delete(medRecordId);
        if (!dao.delete(medRecordId, true)) {
            throw new InternalServerErrorException("Error deleting med record");
        }
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        return dao.get(patientId).stream().map(m -> m.toMedRecordUI(medicationService)).collect(Collectors.toList());
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.get(patientId).isEmpty();
    }

    public void deleteByPatientId(int patientId) {
        dao.getListOfMedRecordsIdsFromPatient(patientId).forEach(id -> {
            medicationService.delete(id);
            dao.delete(id, true);
        });

    }
}