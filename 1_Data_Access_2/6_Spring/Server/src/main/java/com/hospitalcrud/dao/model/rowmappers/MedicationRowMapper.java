package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.Medication;
import org.springframework.jdbc.core.RowMapper;

public class MedicationRowMapper implements RowMapper<Medication> {
    @Override
    public Medication mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Medication medication = new Medication();
        medication.setId(rs.getInt("prescription_id"));
        medication.setMedRecordId(rs.getInt("record_id"));
        medication.setMedicationName(rs.getString("medication_name"));
        return medication;
    }
}