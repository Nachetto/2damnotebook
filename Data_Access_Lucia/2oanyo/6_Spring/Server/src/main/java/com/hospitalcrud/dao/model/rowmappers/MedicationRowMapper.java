package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.Medication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class MedicationRowMapper implements RowMapper<Medication> {
    @Override
    public Medication mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
        Medication medication = new Medication();
        medication.setId(rs.getInt("prescription_id"));
        medication.setMedRecordId(rs.getInt("record_id"));
        medication.setMedicationName(rs.getString("medication_name"));
        return medication;
    }
}