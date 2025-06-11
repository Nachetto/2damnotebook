package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorDAO dao;

    public DoctorService(DoctorDAO dao) {
        this.dao = dao;
    }

    public List<Doctor> getDoctors() {
        return dao.getAll();
    }
}