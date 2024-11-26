package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.rowmappers.MedRecordRowMapper;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("spring")
@Log4j2
public class MedRecordRepository implements MedRecordDAO {

    private final JdbcClient jdbcClient;
    public MedRecordRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<MedRecord> get(int patientId) {
        try {
            return jdbcClient.sql(Constants.GET_MED_RECORDS_BY_PATIENT_ID)
                    .param(1, patientId)
                    .query(new MedRecordRowMapper()).list();
        } catch (Exception e) {
            log.error("Error fetching medical records for patient ID: {}", patientId, e);
            throw new InternalServerErrorException("Error fetching medical records for patient ID: " + patientId + " - " + e.getMessage());
        }
    }

    @Override
    public int save(MedRecord medRecord) {
        try {
            String sql = Constants.INSERT_MED_RECORD;
            return jdbcClient.sql(sql)
                    .param(1, medRecord.getIdPatient())
                    .param(2, medRecord.getIdDoctor())
                    .param(3, medRecord.getDiagnosis())
                    .param(4, medRecord.getDate())
                    .update();
        } catch (Exception e) {
            log.error("Error saving medical record", e);
            return -1;
        }
    }

    @Override
    public void update(MedRecord medRecord) {
        try {
            String sql = Constants.UPDATE_MED_RECORD;
            jdbcClient.sql(sql)
                    .param(1, medRecord.getIdPatient())
                    .param(2, medRecord.getIdDoctor())
                    .param(3, medRecord.getDiagnosis())
                    .param(4, medRecord.getDate())
                    .param(5, medRecord.getId())
                    .update();
        } catch (Exception e) {
            log.error("Error updating medical record with id: {}", medRecord.getId(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try {


            String sql = Constants.DELETE_MED_RECORD;
            return jdbcClient.sql(sql)
                    .param(1, id)
                    .update() > 0;
        } catch (Exception e) {
            throw new InternalServerErrorException(STR."Error deleting medical record with id: \{id} - \{e.getMessage()}");
        }
    }

    @Override
    public List<MedRecord> getAll() {
        try {
            return jdbcClient.sql(Constants.GET_ALL_MED_RECORDS)
                    .query(new MedRecordRowMapper()).list();
        } catch (Exception e) {
            log.error("Error fetching all medical records", e);
            throw new InternalServerErrorException("Error fetching all medical records: " + e.getMessage());
        }
    }

    public MedRecord getById(int id) {
        try {
            Optional<MedRecord> optionalMedRecord = jdbcClient.sql("SELECT * FROM medical_records WHERE record_id = ?")
                    .param(1, id)
                    .query(new MedRecordRowMapper())
                    .optional(); // Devuelve un Optional

            return optionalMedRecord.orElse(null);
        } catch (Exception e) {
            log.error("Error fetching medical record with id: {}", id, e);
            return null;
        }
    }

    public List<Integer> getListOfMedRecordsIdsFromPatient(int patientId) {
        try {
            return jdbcClient.sql("SELECT record_id FROM medical_records WHERE patient_id = ?")
                    .param(1, patientId)
                    .query((rs, rowNum) -> rs.getInt("record_id"))
                    .list();
        } catch (Exception e) {
            log.error("Error fetching medical records for patient ID: {}", patientId, e);
            return List.of(); // Return an empty list in case of an error
        }
    }
}
