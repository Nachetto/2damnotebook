package com.hospitalcrud.dao.mappers;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.stereotype.Component;


@Component
public class DoctorRowMapper {
    public Doctor mapRow(String s) {
        String[] parts = s.split(";");
        if (parts.length < 3) {
            return null;
        }
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String specialty = parts[2];
            return new Doctor(id, name, specialty);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
