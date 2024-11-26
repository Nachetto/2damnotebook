package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.rowmappers.MedRecordRowMapper;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class MedRecordRepository implements MedRecordDAO {

    private final JdbcTemplate jdbcTemplate;
    public MedRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MedRecord> get(int patientId) {
        try {
            String sql = Constants.GET_MED_RECORDS_BY_PATIENT_ID;
            return jdbcTemplate.query(
                    sql,
                    new Object[]{patientId},
                    new MedRecordRowMapper()
            );
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error fetching medical records for patient ID: " + patientId + " - " + e.getMessage());
        }
    }


    @Override
    public int save(MedRecord medRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("1", medRecord.getIdPatient());
        parameters.addValue("2", medRecord.getIdDoctor());
        parameters.addValue("3", medRecord.getDiagnosis());
        parameters.addValue("4", medRecord.getDate());

        int update = namedParameterJdbcTemplate.update(
                Constants.INSERT_MED_RECORD,
                parameters,
                keyHolder,
                new String[]{"record_id"}
        );

        if (update > 0 && keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }


    @Override
    public void update(MedRecord medRecord) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("1", medRecord.getIdPatient());
        parameters.addValue("2", medRecord.getIdDoctor());
        parameters.addValue("3", medRecord.getDiagnosis());
        parameters.addValue("4", medRecord.getDate());
        parameters.addValue("5", medRecord.getId());

        namedParameterJdbcTemplate.update(Constants.UPDATE_MED_RECORD, parameters);
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("?", id);

        return namedParameterJdbcTemplate.update(Constants.DELETE_MED_RECORD, parameters) > 0;
    }





    //not used on this exercise
    @Override
    public List<MedRecord> getAll() {
        try {
            String sql = Constants.GET_ALL_MED_RECORDS;
            return jdbcTemplate.query(sql, new MedRecordRowMapper());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error fetching medical records: " + e.getMessage());
        }
    }
}
