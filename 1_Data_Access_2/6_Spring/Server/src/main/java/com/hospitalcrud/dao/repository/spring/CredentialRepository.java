package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.rowmappers.CredentialRowMapper;
import com.hospitalcrud.dao.repository.CredentialDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("spring")
@Log4j2
public class CredentialRepository implements CredentialDAO {

    public static final String CHECK_USERNAME = "SELECT * FROM user_login WHERE username = ?";
    private final CredentialRowMapper credentialRowMapper;

    private final JdbcClient jdbcClient;
    public CredentialRepository(JdbcClient jdbcClient, CredentialRowMapper credentialRowMapper) {
        this.credentialRowMapper = credentialRowMapper;
        this.jdbcClient = jdbcClient;
    }

    public boolean validateUsername(String username) {
        try {
            Optional<Credential> optionalCredential = jdbcClient.sql(CHECK_USERNAME)
                    .param(1, username)
                    .query(credentialRowMapper)
                    .optional();
            return optionalCredential.isPresent();
        } catch (Exception e) {
            log.error("Error validating username: {}", username, e);
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            Optional<Credential> optionalCredential = jdbcClient.sql("SELECT * FROM user_login WHERE username = ? AND password = ?")
                    .param(1, username)
                    .param(2, password)
                    .query(credentialRowMapper)
                    .optional();
            return optionalCredential.isPresent();
        } catch (Exception e) {
            log.error("Error during login for username: {}", username, e);
            return false;
        }
    }

    public int save(Credential credential) {
        try {
            String sql = "INSERT INTO user_login (username, password, patient_id, doctor_id) VALUES (?, ?, ?, NULL)";
            return jdbcClient.sql(sql)
                    .param(1, credential.getUsername())
                    .param(2, credential.getPassword())
                    .param(3, credential.getPatientId())
                    .update();
        } catch (Exception e) {
            log.error("Error saving credential for username: {}", credential.getUsername(), e);
            return 0;
        }
    }
    @Override
    public boolean delete(int id) {
        String sqlCredential = "DELETE FROM user_login WHERE patient_id = ?";
        return  jdbcClient.sql(sqlCredential)
                .param(1, id)
                .update() > 0;
    }

    //not implemented for this exercise
    @Override
    public List<Credential> getAll() {
        return List.of();
    }

    @Override
    public void update(Credential c) {/*bla bla bla*/}


}
