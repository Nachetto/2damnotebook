package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Medication;
import org.example.nachoHibernateConSpring.dao.repository.MedicationDAO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MedicationService {
    private final MedicationDAO dao;

    public MedicationService(MedicationDAO dao) {
        this.dao = dao;
    }

    public List<String> getMedications(int id) { // get medications for a medRecord
        return dao.findAllById(Collections.singleton(id)).stream().map(Medication::getMedicationName).toList();
    }

    public void delete(int medRecordId) {
        // delete payments as well
        dao.deleteById(medRecordId);
    }

    // not implemented for this exercise, created on client
}