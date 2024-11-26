package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.model.rowmappers.DoctorRowMapper;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("spring")
@Log4j2
public class DoctorRepository  implements DoctorDAO {


    private final JdbcClient jdbcClient;
    private final DoctorRowMapper doctorRowMapper;

    public DoctorRepository(JdbcClient jdbcClient, DoctorRowMapper doctorRowMapper) {
        this.jdbcClient = jdbcClient;
        this.doctorRowMapper = doctorRowMapper;
    }


    @Override
    public List<Doctor> getAll() {
        try {
            return jdbcClient.sql(Constants.GET_ALL_DOCTORS)
                    .query(doctorRowMapper)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching all doctors", e);
            throw new InternalServerErrorException("Error fetching all doctors: " + e.getMessage());
        }
    }

    public int save(Doctor doctor) {
        try {
            String sql = "INSERT INTO doctors (name, specialization, phone) VALUES (?, ?, ?)";
            return jdbcClient.sql(sql)
                    .param(1, doctor.getName())
                    .param(2, doctor.getSpecialty())
                    .param(3, 0)
                    .update();
        } catch (Exception e) {
            log.error("Error saving doctor", e);
            return 0;
        }
    }

    public boolean delete(int id) {
        try {
            String sql = "DELETE FROM doctors WHERE doctor_id = ?";
            return jdbcClient.sql(sql)
                    .param(1, id)
                    .update() > 0;
        } catch (Exception e) {
            log.error("Error deleting doctor with id: {}", id, e);
            return false;
        }
    }

    public Doctor getById(int id) {
        try {
            Optional<Doctor> optionalDoctor = jdbcClient.sql("SELECT * FROM doctors WHERE doctor_id = ?")
                    .param(1, id)
                    .query(doctorRowMapper)
                    .optional();

            return optionalDoctor.orElse(null);
        } catch (Exception e) {
            log.error("Error fetching doctor with id: {}", id, e);
            return null;
        }
    }


    @Override
    public void update(Doctor doctor) {
        //not yet implemented for this exercise
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;//not yet implemented for this exercise
    }
}
