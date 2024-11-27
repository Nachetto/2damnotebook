package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
@Profile("JDBC")
@Log4j2
public class CredentialRepository implements CredentialDAO {
    private final DBConnection db;

    public CredentialRepository(DBConnection db) {
        this.db = db;
    }

    public boolean validateUsername(String username) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.CHECK_USERNAME)){
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                if (rs.getString("username").equals(username)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error validating username: {}", username, e);
            return false;
        }
    }

    public boolean login(String username, String password) {
        try(Connection con = db.getHikariDataSource().getConnection();
            Statement stmt = con.createStatement();
            PreparedStatement preparedStatement = con.prepareStatement(Constants.LOGIN)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            log.error("Error during login for username: {}", username, e);
            return false;
        }
    }

    public int save(Credential credential) {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.ADD_CREDENTIAL)){
            preparedStatement.setString(1, credential.getUsername());
            preparedStatement.setString(2, credential.getPassword());
            preparedStatement.setInt(3, credential.getPatientId());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("Error saving credential: ", e);
            return 0;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.DELETE_CREDENTIAL)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("Error saving credential: ", e);
            return false;
        }
    }








    //not implemented for this exercise
    @Override
    public List<Credential> getAll() {
        return List.of();
    }
    @Override
    public void update(Credential c) {/*bla bla bla*/}
}
