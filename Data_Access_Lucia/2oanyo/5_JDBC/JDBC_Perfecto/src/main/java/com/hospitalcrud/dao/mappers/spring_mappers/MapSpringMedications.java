package com.hospitalcrud.dao.mappers.spring_mappers;

import com.hospitalcrud.dao.model.Medication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapSpringMedications implements RowMapper<Medication> {
    @Override
    public Medication mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Medication(
                rs.getString("medication_name"),
                rs.getInt("record_id"),
                rs.getString("dosage")
        );
    }
}
