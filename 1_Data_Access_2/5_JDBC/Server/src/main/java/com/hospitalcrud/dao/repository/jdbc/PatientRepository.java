package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class PatientRepository implements PatientDAO {

    private final DataSource dataSource;

    public PatientRepository(DBConnection dbConnection) {
        this.dataSource = dbConnection.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Patient> getAll() {
        String sql = Constants.GET_ALL_PATIENTS;
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getInt("patient_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date_of_birth").toLocalDate(),
                        resultSet.getString("phone")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            log.error("Error fetching patients: ", e);
            throw new InternalServerErrorException("Error fetching patients: " + e.getMessage());
        }
        return patients;
    }

    @Override
    public int save(Patient patient) {
        String sql = Constants.INSERT_PATIENT;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(3, patient.getPhone());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting patient failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting patient failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error("Error saving patient: ", e);
            throw new InternalServerErrorException("Error saving patient: " + e.getMessage());
        }
    }

    @Override
    public void update(Patient patient) {
        String sql = Constants.UPDATE_PATIENT;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, patient.getName());
            preparedStatement.setDate(2, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(3, patient.getPhone());
            preparedStatement.setInt(4, patient.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Error updating patient: ", e);
            throw new InternalServerErrorException("Error updating patient: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) {
            throw new IllegalArgumentException("Delete operation not confirmed.");
        }

        String sql = Constants.DELETE_PATIENT;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            log.error("Error deleting patient: ", e);
            throw new InternalServerErrorException("Error deleting patient: " + e.getMessage());
        }
    }
}
