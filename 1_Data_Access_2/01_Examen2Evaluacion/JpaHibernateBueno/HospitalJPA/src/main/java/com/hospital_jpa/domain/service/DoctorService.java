package com.hospital_jpa.domain.service;

import com.hospital_jpa.dao.interfaces.DoctorsRepository;
import com.hospital_jpa.domain.model.DoctorUI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorsRepository doctorsRepository;

    public DoctorService(DoctorsRepository doctorsRepository) {
        this.doctorsRepository = doctorsRepository;
    }
    public List<DoctorUI> getAll() {
        List<DoctorUI> doctors = new ArrayList<>();
        doctorsRepository.getAll().forEach(d -> doctors.add(new DoctorUI(d.getDoctorId(),d.getName())));
        return doctors;
    }
}
