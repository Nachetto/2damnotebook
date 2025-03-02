package com.hospitalcrud.dao.mappers.spring_mappers;

import com.hospitalcrud.dao.model.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapSpringPatients implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("phone"),
                0);
    }
}
