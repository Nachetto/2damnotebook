package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Log4j2
public class DoctorRepository  implements DoctorDAO {

    private final DBConnection db;
    public DoctorRepository(DBConnection db) {
        this.db = db;
    }


    @Override
    public List<Doctor> getAll() {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery(Constants.GET_ALL_DOCTORS);
            List<Doctor> doctors = new ArrayList<>();
            while (rs.next()) {
                Doctor doctor = new Doctor( //"doctor_id", "name", "specialization", "phone"
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization")
                );
                doctors.add(doctor);
            }
            return doctors;
        } catch (Exception e) {
            log.error("Error fetching all doctors", e);
            throw new InternalServerErrorException("Error fetching all doctors: " + e.getMessage());
        }
    }












    //not yet implemented for this exercise
    public int save(Doctor doctor) {
        /*
        try {
            String sql = ADD_DOCTORS;
            return jdbcClient.sql(sql)
                    .param(1, doctor.getName())
                    .param(2, doctor.getSpecialty())
                    .param(3, 0)
                    .update();
        } catch (Exception e) {
            log.error("Error saving doctor", e);
            return 0;
        }*/return 0;
    }

    public boolean delete(int id) {
        /*
        try {
            String sql = DELETE_DOCTORS;
            return jdbcClient.sql(sql)
                    .param(1, id)
                    .update() > 0;
        } catch (Exception e) {
            log.error("Error deleting doctor with id: {}", id, e);
            return false;
        }*/return false;
    }
    public Doctor getById(int id) {
        /*
        try {
            Optional<Doctor> optionalDoctor = jdbcClient.sql(GETDOCTORBYID)
                    .param(1, id)
                    .query(doctorRowMapper)
                    .optional();

            return optionalDoctor.orElse(null);
        } catch (Exception e) {
            log.error("Error fetching doctor with id: {}", id, e);
            return null;
        }*/return null;
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
