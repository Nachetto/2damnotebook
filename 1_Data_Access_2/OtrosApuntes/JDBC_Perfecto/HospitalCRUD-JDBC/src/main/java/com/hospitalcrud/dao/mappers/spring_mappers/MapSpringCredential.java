package com.hospitalcrud.dao.mappers.spring_mappers;

import com.hospitalcrud.dao.model.Credential;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapSpringCredential implements RowMapper<Credential> {
    @Override
    public Credential mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Credential(
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id")
        );
    }
}
