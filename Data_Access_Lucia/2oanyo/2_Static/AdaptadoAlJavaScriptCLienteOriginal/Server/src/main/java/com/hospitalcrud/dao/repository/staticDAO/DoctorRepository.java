package com.hospitalcrud.dao.repository.staticDAO;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.domain.model.PatientUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {
    private static List<Doctor> doctors = new ArrayList<>(List.of(
            new Doctor(1, "Dr. House", "Oncology"),
            new Doctor(2, "Dr. Strange", "Neurology"),
            new Doctor(3, "Dr. Who", "Cardiology"),
            new Doctor(4, "Dr. Jekyll", "Psychiatry")
    ));
    public List<Doctor> getDoctors() {
        return doctors;
    }
}
