package com.hospitalcrud.dao.mappers.spring_mappers;

import com.hospitalcrud.dao.model.MedicalRecord;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapSpringMedicalRecords implements RowMapper<MedicalRecord> {
    @Override
    public MedicalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MedicalRecord(
                rs.getInt("record_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getString("diagnosis"),
                rs.getDate("admission_Date").toLocalDate()
        );
    }
}
