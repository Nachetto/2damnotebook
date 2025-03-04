package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.Patient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class PatientRowMapper  implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("phone")
        );
    }
}
