package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Patient;
import org.bson.types.ObjectId;

import java.util.List;


public interface PatientRepository {
    List<Patient> getAll();
    ObjectId save(Patient patient);
    void update(Patient patient);
    void delete(ObjectId patientId,boolean confirmation);
}
