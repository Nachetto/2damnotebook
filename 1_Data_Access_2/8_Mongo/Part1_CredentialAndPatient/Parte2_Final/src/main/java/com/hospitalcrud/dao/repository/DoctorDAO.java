package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.Doctor;

import java.util.List;

public interface DoctorDAO {

    List<Doctor> getAll();
    int save(Doctor m);
    void update(Doctor m);
    boolean delete(int id, boolean confirmation);
    Doctor getById(int idDoctor);
}

