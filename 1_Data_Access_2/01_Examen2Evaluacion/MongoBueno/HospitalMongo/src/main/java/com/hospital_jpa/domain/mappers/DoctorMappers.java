package com.hospital_jpa.domain.mappers;

import com.hospital_jpa.dao.model.Doctor;
import com.hospital_jpa.domain.model.DoctorUI;
import org.springframework.stereotype.Component;

@Component
public class DoctorMappers {
    public DoctorUI toDoctorUI (Doctor doctor, int id) {
        return new DoctorUI(id,doctor.getName());
    }
}
