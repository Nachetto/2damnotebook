package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {
    private final MedicationDAO dao;

    public MedicationService(MedicationDAO dao) {
        this.dao = dao;
    }



    public int add(List<String> medications, int medRecordId) {
        medications.forEach(
                //a query is executed for adding each medication, not efficient
                m -> dao.save(
                        new Medication(0, m, medRecordId)
                )
        );

        return 1;
    }

    public void update(List<String> medications, int medRecordId) {
        dao.delete(medRecordId);
        add(medications, medRecordId);
    }

    public List<String> getMedications(int id) {//get medications for a medRecord
        return dao.get(id).stream().map(Medication::getMedicationName).toList();
    }

    public void delete(int medRecordId) {
        dao.delete(medRecordId);
    }

    // not implemented for this exercise, created on client
}
