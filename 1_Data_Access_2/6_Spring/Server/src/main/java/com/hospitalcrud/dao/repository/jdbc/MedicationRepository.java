package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class MedicationRepository implements MedicationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Medication> medicationRowMapper = new BeanPropertyRowMapper<>(Medication.class);

    public MedicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Rowmapper to cast the names of the columns to the names of the fields in the Medication class
    private static class MedicationRowMapper implements RowMapper<Medication> {
        @Override
        public Medication mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Medication medication = new Medication();
            medication.setId(rs.getInt("prescription_id"));
            medication.setMedRecordId(rs.getInt("record_id"));
            medication.setMedicationName(rs.getString("medication_name"));
            return medication;
        }
    }

    @Override
    public int save(Medication m) {
        try {
            String sql = "INSERT INTO prescribed_medications (record_id, medication_name, dosage) VALUES (?,?,null)";
            return jdbcTemplate.update(sql, m.getMedRecordId(), m.getMedicationName());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void update(Medication m) {
        try {
            String sql = "UPDATE prescribed_medications SET medication_name = ? WHERE prescription_id = ?";
            jdbcTemplate.update(sql, m.getMedicationName(), m.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) { //remove all medications from a record
        try {
            String sql = "DELETE FROM prescribed_medications WHERE record_id = ?";
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while deleting all medications from the record,, cause: "+e.getMessage());
        }
    }

    @Override
    public List<Medication> get(int medRecordId) {
        try {
            String sql = "SELECT * FROM prescribed_medications WHERE record_id = ?";
            return jdbcTemplate.query(sql, new Object[]{medRecordId}, new MedicationRowMapper());
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while getting medications for record with id: "+medRecordId+", cause: "+e.getMessage());
        }
    }



    //This method is not used in the project
    @Override
    public List<Medication> getAll() {
        return List.of();
    }

}
