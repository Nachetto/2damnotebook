package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Patient;

import java.util.List;

public interface PatientDAO {
    List<Patient> getAll();
    int save(Patient m);
    void update(Patient m);
    boolean delete(int id, boolean confirmation);
}
