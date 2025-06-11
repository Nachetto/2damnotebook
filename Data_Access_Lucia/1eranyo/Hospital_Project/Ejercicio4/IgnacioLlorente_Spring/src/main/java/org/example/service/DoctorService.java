package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.DoctorDaoImpl;
import org.example.domain.Doctor;

import java.util.List;

public class DoctorService {
    private final DoctorDaoImpl doctorDao;
    @Inject
    public DoctorService(DoctorDaoImpl doctorDao) {
        this.doctorDao = doctorDao;
    }

    public Either<String, List<Doctor>> getAll() {
        return doctorDao.getAll();
    }

    public Either<String, Doctor> get(int id) {
        return doctorDao.get(id);
    }

    public int save(Doctor d) {
        return doctorDao.save(d);
    }

    public int modify(Doctor initialdoctor, Doctor modifieddoctor) {
        return doctorDao.modify(initialdoctor, modifieddoctor);
    }

    public int delete(Doctor d) {
        return doctorDao.delete(d);
    }

    public int getDoctorIDFromUsername(String username) {
        return doctorDao.getDoctorIDFromUsername(username);
    }
}
