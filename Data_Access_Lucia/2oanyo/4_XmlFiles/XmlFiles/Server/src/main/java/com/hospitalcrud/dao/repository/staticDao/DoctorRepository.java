package com.hospitalcrud.dao.repository.staticDao;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class DoctorRepository implements DoctorDAO {

    private static List<Doctor> doctors = new ArrayList<>(List.of(
            new Doctor(1, "Dr. House", "Oncology"),
            new Doctor(2, "Dr. Strange", "Neurology"),
            new Doctor(3, "Dr. Who", "Cardiology"),
            new Doctor(4, "Dr. Jekyll", "Psychiatry")
    ));
    public List<Doctor> getDoctors() {
        return doctors;
    }

    @Override
    public List<Doctor> getAll() {
        return List.of();
    }

    @Override
    public int save(Doctor m) {
        return 0;
    }

    @Override
    public void update(Doctor m) {

    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
