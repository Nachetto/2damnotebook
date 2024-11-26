package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.MedRecord;
import org.springframework.jdbc.core.RowMapper;

public class MedRecordRowMapper implements RowMapper<MedRecord> {
    @Override
    public MedRecord mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new MedRecord(
                rs.getInt("record_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getString("diagnosis"),
                rs.getDate("admission_date").toLocalDate()
        );
    }
}
