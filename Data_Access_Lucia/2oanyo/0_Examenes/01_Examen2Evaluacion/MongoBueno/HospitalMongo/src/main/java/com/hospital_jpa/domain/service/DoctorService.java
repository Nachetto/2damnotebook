package com.hospital_jpa.domain.service;

import com.hospital_jpa.dao.interfaces.DoctorsRepository;
import com.hospital_jpa.dao.model.Doctor;
import com.hospital_jpa.domain.mappers.DoctorMappers;
import com.hospital_jpa.domain.model.DoctorUI;
import com.hospital_jpa.domain.utils.IdManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorsRepository doctorsRepository;
    private final IdManager idManager;
    private final DoctorMappers mappers;

    public DoctorService(DoctorsRepository doctorsRepository, IdManager idManager, DoctorMappers mappers) {
        this.doctorsRepository = doctorsRepository;
        this.idManager = idManager;
        this.mappers = mappers;
    }

    public List<DoctorUI> getAll() {
        List<Doctor> doctors = doctorsRepository.getAll();
        idManager.fillDoctorIds(doctors);
        return doctors.stream().map(p -> mappers.toDoctorUI(p,idManager.getDoctorIds().get(p.getId()))).toList();
    }
}
