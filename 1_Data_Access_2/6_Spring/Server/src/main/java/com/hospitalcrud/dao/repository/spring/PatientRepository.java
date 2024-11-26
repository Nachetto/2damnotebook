package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.model.rowmappers.PatientRowMapper;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class PatientRepository implements PatientDAO {
    private final JdbcClient jdbcClient;

    public PatientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }



    @Override
    public List<Patient> getAll() {
        try {
            return jdbcClient.sql(Constants.GET_ALL_PATIENTS)
                    .query(new PatientRowMapper()).list(); // Devuelve la lista de pacientes
        } catch (Exception e) {
            log.error("Error fetching all patients", e);
            throw new InternalServerErrorException("Error fetching all patients: " + e.getMessage());
        }
    }

    @Override
    public int save(Patient patient) {
        try {
            String sql = Constants.INSERT_PATIENT;
            return jdbcClient.sql(sql)
                    .param("name", patient.getName())
                    .param("birthdate", Date.valueOf(patient.getBirthDate()))
                    .param("phone", patient.getPhone())
                    .update(); // Devuelve el número de filas afectadas
        } catch (Exception e) {
            log.error("Error saving patient", e);
            return -1; // Retorna -1 si ocurre un error
        }
    }

    @Override
    public void update(Patient patient) {
        try {
            String sql = Constants.UPDATE_PATIENT;
            jdbcClient.sql(sql)
                    .param("name", patient.getName())
                    .param("birthdate", Date.valueOf(patient.getBirthDate()))
                    .param("phone", patient.getPhone())
                    .param("id", patient.getId())
                    .update(); // Ejecuta la actualización
        } catch (Exception e) {
            log.error("Error updating patient with id: {}", patient.getId(), e);
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        try {
            String sql = Constants.DELETE_PATIENT;
            return jdbcClient.sql(sql)
                    .param("id", id)
                    .update() > 0; // Devuelve true si se elimina el paciente
        } catch (Exception e) {
            log.error("Error deleting patient with id: {}", id, e);
            return false; // Retorna false si ocurre un error
        }
    }

    public int getPatientId(String name) {
        try {
            String sql = Constants.GET_PATIENT_ID;
            return jdbcClient.sql(sql)
                    .param(1, name)
                    .query(Integer.class)
                    .optional().orElse(-1); // Devuelve el id del paciente o -1 si no se encuentra
        } catch (Exception e) {
            log.error("Error fetching patient id for name: {}", name, e);
            return -1; // Si ocurre un error, devuelve Optional vacío
        }
    }


}

