package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Patient;

import java.util.List;


public interface PatientRepository {
    List<Patient> getAll();
    int save(Patient patient);
    void update(Patient patient);
    void delete(int patientId,boolean confirmation);
}
