package com.hospitalcrud.domain.service;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.respositories.MedicationsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationsService {

    private final MedicationsRepository medicationsRepository;

    public MedicationsService(MedicationsRepository medicationsRepository) {
        this.medicationsRepository = medicationsRepository;
    }

    public List<String> getAll() {
        List<String> medications = new ArrayList<>();
        medicationsRepository.getAll().forEach(m -> medications.add(m.getMedicationName()));
        return medications;
    }
}
