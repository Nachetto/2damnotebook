package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Doctor;

import java.util.List;

public interface DoctorsRepository {
    List<Doctor> getAll();
}
