package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DoctorRowMapper implements RowMapper<Doctor> {
    @Override
    public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Doctor(
                rs.getInt("doctor_id"),
                rs.getString("name"),
                rs.getString("specialization")
        );
    }
}
