package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.Doctor;
import org.bson.types.ObjectId;

import java.util.List;

public interface DoctorDAO {

    List<Doctor> getAll();

    Doctor get(ObjectId idDoctor);

    int save(Doctor m);

    void update(Doctor m);

    boolean delete(int id, boolean confirmation);
}
