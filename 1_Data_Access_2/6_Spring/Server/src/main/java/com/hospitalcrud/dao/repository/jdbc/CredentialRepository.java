package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("spring")
@Log4j2
public class CredentialRepository implements CredentialDAO {

    public static final String CHECK_USERNAME = "SELECT * FROM user_login WHERE username = ?";


    private final JdbcTemplate jdbcTemplate;
    public CredentialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class CredentialRowMapper implements RowMapper<Credential> {
        @Override
        public Credential mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
            return new Credential(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("patient_id")
            );
        }
    }

    public boolean validateUsername(String username) {
        try {
            String sql = CHECK_USERNAME;
            List<Credential> credentials = jdbcTemplate.query(sql, new CredentialRowMapper(), username);
            return credentials.size() == 1;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean login(String username,String  password) {
        try {
            String sql = "SELECT * FROM user_login WHERE username = ? AND password = ?";
            List<Credential> credentials = jdbcTemplate.query(sql, new CredentialRowMapper(), username, password);
            return credentials.size() == 1;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }


    //not implemented for this exercise
    @Override
    public List<Credential> getAll() {
        return List.of();
    }
    @Override
    public int save(Credential c) {
        return 0;
    }
    @Override
    public void update(Credential c) {/*bla bla bla*/}
    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }

}
