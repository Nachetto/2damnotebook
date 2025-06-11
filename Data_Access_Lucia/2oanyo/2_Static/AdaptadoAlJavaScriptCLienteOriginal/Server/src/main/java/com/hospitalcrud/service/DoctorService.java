package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.staticDAO.DoctorRepository;
import com.hospitalcrud.domain.model.PatientUI;

import java.util.List;

public class DoctorService {
    private DoctorRepository dao;
    public DoctorService() {
        this.dao = new DoctorRepository();
    }
    public List<Doctor> getDoctors() {
        return dao.getDoctors();
    }
    // not implemented for this exercise, created on client
}
