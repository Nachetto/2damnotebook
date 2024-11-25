package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class DoctorRepository  implements DoctorDAO {

    private final JdbcTemplate jdbcTemplate;
    public DoctorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class DoctorRowMapper implements RowMapper<Doctor> {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization")
            );
        }
    }

    @Override
    public List<Doctor> getAll() {
        try {
            String sql = Constants.GET_ALL_DOCTORS;
            return jdbcTemplate.query(sql, new DoctorRowMapper());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error fetching doctors: " + e.getMessage());
        }
    }




    @Override
    public int save(Doctor m) {
        //not yet implemented for this exercise
        return 0;
    }
    @Override
    public void update(Doctor doctor) {
        //not yet implemented for this exercise
    }
    @Override
    public boolean delete(int id, boolean confirmation) {
        //not yet implemented for this exercise
        return false;
    }
}
