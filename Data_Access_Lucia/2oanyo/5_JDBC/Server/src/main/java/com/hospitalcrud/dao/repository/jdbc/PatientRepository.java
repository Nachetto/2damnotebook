package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Log4j2
public class PatientRepository implements PatientDAO {
    private final DBConnection db;

    public PatientRepository(DBConnection db) {
        this.db = db;
    }


    @Override
    public List<Patient> getAll() {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(Constants.GET_ALL_PATIENTS);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {//esto se puede hacer con el mapper pero yo lo hago a pelo
                patients.add(new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("phone")
                ));
            }
            return patients;
        } catch (Exception e) {
            log.error("Error fetching all patients", e);
            throw new InternalServerErrorException("Error fetching all patients: " + e.getMessage());
        }
    }

    @Override
    public int save(Patient patient) {
        //transaction, first insert patient, then retrieve the id of the patient, then save credentials with the patient id, if any of the steps fails, rollback
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.INSERT_PATIENT);
             PreparedStatement preparedStatement2 = con.prepareStatement(Constants.RETRIEVE_PATIENT_ID);
             PreparedStatement preparedStatement3 = con.prepareStatement(Constants.ADD_CREDENTIAL)) {
            int patientId;
            try {
                con.setAutoCommit(false);
                preparedStatement.setString(1, patient.getName());
                preparedStatement.setDate(2, Date.valueOf(patient.getBirthDate()));
                preparedStatement.setString(3, patient.getPhone());
                if (preparedStatement.executeUpdate() > 0) {
                        preparedStatement2.setString(1, patient.getName());
                        preparedStatement2.setDate(2, Date.valueOf(patient.getBirthDate()));
                        preparedStatement2.setString(3, patient.getPhone());
                        ResultSet rs = preparedStatement2.executeQuery();
                        rs.next();
                        patientId = rs.getInt("patient_id");
                } else {
                    throw new InternalServerErrorException("Error saving patient");
                }

                preparedStatement3.setString(1, patient.getCredential().getUsername());
                preparedStatement3.setString(2, patient.getCredential().getPassword());
                preparedStatement3.setInt(3, patientId);
                if (preparedStatement3.executeUpdate() > 0) {
                    con.commit();
                    return patientId;
                } else {
                    throw new InternalServerErrorException("Error saving credential");
                }
            } catch (Exception e) {
                con.rollback();
                log.error("Error saving patient", e);
                return -1;
            }

        } catch (Exception e) {
            log.error("Error saving patient", e);
            return -1;

        }
    }


    @Override
    public void update(Patient patient) {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement();
             //"UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?"
             PreparedStatement preparedStatement = con.prepareStatement(Constants.UPDATE_PATIENT)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(3, patient.getPhone());
            preparedStatement.setInt(4, patient.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new InternalServerErrorException("Error updating patient with id:"+patient.getId());
            }
        } catch (Exception e) {
            log.error("Error updating patient with id: {}", patient.getId(), e);
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement1 = con.prepareStatement(Constants.DELETE_APPOINTMENTS);
             PreparedStatement preparedStatement2 = con.prepareStatement(Constants.DELETE_PAYMENT);
             Statement getAllRecordIdsStmt = con.createStatement();
             PreparedStatement preparedStatement3 = con.prepareStatement(Constants.DELETE_MEDICATION);
             PreparedStatement preparedStatement4 = con.prepareStatement(Constants.DELETE_PATIENT_MED_RECORDS);
             PreparedStatement preparedStatement5 = con.prepareStatement(Constants.DELETE_CREDENTIAL);
             PreparedStatement preparedStatement6 = con.prepareStatement(Constants.DELETE_PATIENT)
        ) {
            //delete appointments, payments, medications of the medical record, medical records,
            // credentials and finally patient in a transaction
            try {
                con.setAutoCommit(false); // Start a transaction
                preparedStatement1.setInt(1, id);
                preparedStatement1.executeUpdate();

                preparedStatement2.setInt(1, id);
                preparedStatement2.executeUpdate();

                ResultSet rs = getAllRecordIdsStmt.executeQuery("SELECT record_id FROM medical_records WHERE patient_id = " + id);
                while (rs.next()) {
                    preparedStatement3.setInt(1, rs.getInt("record_id"));
                    preparedStatement3.executeUpdate();
                }

                preparedStatement4.setInt(1, id);
                preparedStatement4.executeUpdate();

                preparedStatement5.setInt(1, id);
                preparedStatement5.executeUpdate();

                preparedStatement6.setInt(1, id);
                preparedStatement6.executeUpdate();

                con.commit();
                return true;
            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                throw new InternalServerErrorException("Error deleting patient with id: " + id + " - " + e.getMessage());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Error deleting patient with id: " + id + " - " + e.getMessage());
        }
    }
}

