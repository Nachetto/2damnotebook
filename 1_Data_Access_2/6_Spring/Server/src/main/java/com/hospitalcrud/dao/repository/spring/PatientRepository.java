package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class PatientRepository implements PatientDAO {
    private final JdbcTemplate jdbcTemplate;

    public PatientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //Rowmapper to cast the names of the columns to the names of the fields in the Patient class
    private static class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("phone")
            );
        }
    }

    @Override
    public List<Patient> getAll() {
        try {
            String sql = Constants.GET_ALL_PATIENTS;
            return jdbcTemplate.query(sql, new PatientRowMapper());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error fetching patients: " + e.getMessage());
        }
    }

    @Override
    public int save(Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", patient.getName());
        parameters.addValue("birthdate", Date.valueOf(patient.getBirthDate()));
        parameters.addValue("phone", patient.getPhone());

        int update = namedParameterJdbcTemplate.update(Constants.INSERT_PATIENT, parameters, keyHolder, new String[]{"id"});

        if (update > 0 && keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        }
        return -1;
    }

    @Override
    public void update(Patient patient) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", patient.getName());
        parameters.addValue("birthdate", Date.valueOf(patient.getBirthDate()));
        parameters.addValue("phone", patient.getPhone());
        parameters.addValue("id", patient.getId());

        namedParameterJdbcTemplate.update(Constants.UPDATE_PATIENT, parameters);
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);

        return namedParameterJdbcTemplate.update(Constants.DELETE_PATIENT, parameters) > 0;
    }

    public int getPatientId(String name) {
        try {
            String sql = Constants.GET_PATIENT_ID;
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, Integer.class);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error fetching patient id: " + e.getMessage());
        }
    }

}

