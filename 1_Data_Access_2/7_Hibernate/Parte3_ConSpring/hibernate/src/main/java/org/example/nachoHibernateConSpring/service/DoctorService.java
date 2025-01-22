package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Doctor;
import org.example.nachoHibernateConSpring.dao.repository.DoctorDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorDAO dao;

    public DoctorService(DoctorDAO dao) {
        this.dao = dao;
    }

    public List<Doctor> getDoctors() {
        return dao.findAll();
    }


    public Doctor getDoctor(int doctorId) {
        return dao.getById(doctorId);
    }
}