package com.hospitalcrud.dao.model.rowmappers;

import com.hospitalcrud.dao.model.Credential;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class CredentialRowMapper implements RowMapper<Credential> {
    @Override
    public Credential mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        return new Credential(
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("patient_id")
        );
    }
}
