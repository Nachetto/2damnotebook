package com.hospitalcrud.dao.mappers.jdbc_mappers;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapDoctors {
    public List<Doctor> mapDoctors(ResultSet rs) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            while (rs.next()) doctors.add(new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("phone")));
            return doctors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> mapDoctorsNames(ResultSet resultSet) {
        List<String> doctors = new ArrayList<>();
        try {
            while (resultSet.next())
                doctors.add(resultSet.getString("name"));
            return doctors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
