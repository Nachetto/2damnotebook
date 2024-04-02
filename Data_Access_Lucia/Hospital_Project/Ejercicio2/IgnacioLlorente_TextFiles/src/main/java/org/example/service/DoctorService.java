package org.example.service;

import io.vavr.control.Either;
import org.example.dao.impl.DoctorDaoImpl;
import org.example.domain.Doctor;

import javax.inject.Inject;
import java.util.List;

public class DoctorService {
    private DoctorDaoImpl doctorDao;
    public DoctorService() {
        this.doctorDao = new DoctorDaoImpl();
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
}
