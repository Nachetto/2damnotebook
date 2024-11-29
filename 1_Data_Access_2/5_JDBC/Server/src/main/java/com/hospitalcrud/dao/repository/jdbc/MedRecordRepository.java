package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repository.MedRecordDAO;
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
public class MedRecordRepository implements MedRecordDAO {
    private final DBConnection db;

    public MedRecordRepository(DBConnection db) {
        this.db = db;
    }


    @Override
    public List<MedRecord> get(int patientId) { // MEDICAL RECORDS FROM PATIENT ID
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.GET_MED_RECORDS_BY_PATIENT_ID)) {
            preparedStatement.setInt(1, patientId);
            ResultSet rs = preparedStatement.executeQuery();
            return medicalRecordsFromDatabase(rs);
        } catch (Exception e) {
            log.error("Error fetching medical records for patient ID: {}", patientId, e);
            throw new InternalServerErrorException("Error fetching medical records for patient ID: " + patientId + " - " + e.getMessage());
        }
    }

    /*

     */
    @Override
    public int save(MedRecord medRecord) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.INSERT_MED_RECORD)) {
            preparedStatement.setInt(1, medRecord.getIdPatient());
            preparedStatement.setInt(2, medRecord.getIdDoctor());
            preparedStatement.setString(3, medRecord.getDiagnosis());
            preparedStatement.setDate(4, Date.valueOf(medRecord.getDate()));
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("Error saving medical record", e);
            return -1;
        }
    }

    @Override
    public void update(MedRecord medRecord) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.UPDATE_MED_RECORD)) {
            preparedStatement.setInt(1, medRecord.getIdPatient());
            preparedStatement.setInt(2, medRecord.getIdDoctor());
            preparedStatement.setString(3, medRecord.getDiagnosis());
            preparedStatement.setDate(4, Date.valueOf(medRecord.getDate()));
            preparedStatement.setInt(5, medRecord.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new InternalServerErrorException("Error updating medical record with id: " + medRecord.getId());
            }
        } catch (Exception e) {
            log.error("Error updating medical record with id: {}", medRecord.getId(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.DELETE_MED_RECORD)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error deleting medical record with id: " + id + " - " + e.getMessage());
        }
    }

    @Override
    public List<MedRecord> getAll() {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(Constants.GET_ALL_MED_RECORDS);
            return medicalRecordsFromDatabase(rs);
        } catch (Exception e) {
            log.error("Error fetching all medical records", e);
            throw new InternalServerErrorException("Error fetching all medical records: " + e.getMessage());
        }

    }

    private List<MedRecord> medicalRecordsFromDatabase(ResultSet rs) throws SQLException {
        List<MedRecord> medRecords = new ArrayList<>();
        while (rs.next()) {
            medRecords.add(new MedRecord(
                    rs.getInt("record_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("diagnosis"),
                    rs.getDate("admission_date").toLocalDate()
            ));
        }
        return medRecords;
    }


    public List<Integer> getListOfMedRecordsIdsFromPatient(int patientId) {
        try (Connection con = db.getHikariDataSource().getConnection();
             Statement stmt = con.createStatement();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT record_id FROM medical_records WHERE patient_id = ?")) {
            preparedStatement.setInt(1, patientId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Integer> recordIds = new ArrayList<>();
            while (rs.next()) {
                recordIds.add(rs.getInt("record_id"));
            }
            return recordIds;
        } catch (Exception e) {
            log.error("Error fetching medical records for patient ID: {}", patientId, e);
            return List.of(); // Return an empty list in case of an error
        }
    }
}

