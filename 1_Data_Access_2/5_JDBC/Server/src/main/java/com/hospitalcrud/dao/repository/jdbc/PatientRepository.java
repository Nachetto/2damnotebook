package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
@Log4j2
public class PatientRepository implements PatientDAO {

    private ResultSet rs;
    private PreparedStatement pStmt;
    private DBConnection dbConnection;


    public PatientRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Patient> getAll() {
        try (Connection con = dbConnection.getConnection()) {
            pStmt = con.prepareStatement(Constants.GET_ALL_PATIENTS);
            rs = pStmt.executeQuery();
            return readRS(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public int save(Patient m) {
        try (Connection con = dbConnection.getConnection()) {
            pStmt = con.prepareStatement(Constants.INSERT_PATIENT);
            pStmt.setString(1, m.getName());
            pStmt.setDate(2, java.sql.Date.valueOf(m.getBirthDate()));
            pStmt.setString(3, m.getPhone());
            return pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void update(Patient m) {
        try (Connection con = dbConnection.getConnection()) {
            pStmt = con.prepareStatement(Constants.UPDATE_PATIENT);
            pStmt.setString(1, m.getName());
            pStmt.setDate(2, java.sql.Date.valueOf(m.getBirthDate()));
            pStmt.setString(3, m.getPhone());
            pStmt.setInt(4, m.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }

    private ArrayList<Patient> readRS(ResultSet rs) {
        ArrayList<Patient> listPatients = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("patient_id");
                String name = rs.getString("name");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                String phone = rs.getString("phone");
                listPatients.add(new Patient(id, name, dateOfBirth, phone));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return listPatients;
    }
}
