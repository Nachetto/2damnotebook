package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.Patient;
import org.bson.types.ObjectId;

import java.util.List;

public interface PatientDAO {
    List<Patient> getAll();
    int save(Patient m);
    void update(Patient m);
    int delete(ObjectId id, boolean confirmation);
}
