package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.Medication;

import java.util.List;

public interface MedicationDAO {
    List<Medication> getAll();
    int save(Medication m);
    void update(Medication m);
    void delete(int id, boolean confirmation);
}
