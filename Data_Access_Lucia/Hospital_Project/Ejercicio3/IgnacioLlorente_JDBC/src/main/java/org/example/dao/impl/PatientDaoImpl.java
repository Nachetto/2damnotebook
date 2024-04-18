package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.PatientDao;
import org.example.dao.common.DBConnection;
import org.example.dao.common.SQLConstants;
import org.example.domain.Credential;
import org.example.domain.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PatientDaoImpl implements PatientDao {
    private final DBConnection db;

    @Inject
    public PatientDaoImpl(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<String, List<Patient>> getAll() {
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.CREDENTIALSFROMPATIENTORDOCTORID_QUERY)) {

            ResultSet rs = stmt.executeQuery(SQLConstants.GETALLPATIENTS_QUERY);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt("PatientID"));
                ResultSet credentialsResult = preparedStatement.executeQuery();
                if (!credentialsResult.next()) {
                    log.error("No credentials found for patient with id " + rs.getInt("PatientID"));
                    return Either.left(Constantes.PATIENTDBERROR + "No credentials found for patient with id " + rs.getInt("id"));
                }
                patients.add(newPatientFromDB(rs, credentialsResult));
            }
            return Either.right(patients);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    private Patient newPatientFromDB(ResultSet rs, ResultSet credentialsResult) throws SQLException {
        return new Patient(
                rs.getInt("PatientID"),
                rs.getString("Name"),
                rs.getString("ContactDetails"),
                rs.getString("PersonalInformation"),
                new Credential(
                        credentialsResult.getString("username"),
                        credentialsResult.getString("password")
                )
        );
    }

    public Either<String, Integer> getTotalAmmountPayed(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.TOTAL_AMMOUNT_PAID_BY_PATIENT)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Either.right(rs.getInt("TotalAmountPaid"));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return Either.left(Constantes.DATABASEERR + ex.getMessage());
        }
    }

    public Either<String, List<Patient>> getAllPatientsWithTotalAmmountPaid() {
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLConstants.GETALLPATIENTSWITHTOTALAMMOUNTPAID_QUERY);
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("PatientID"),
                        rs.getString("Name"),
                        rs.getString("ContactDetails"),
                        rs.getString("PersonalInformation"),
                        rs.getInt("TotalAmountPaid")
                ));
            }
            return Either.right(patients);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    public Either<String, Patient> get(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.GETFROMPATIENTID_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Either.right(new Patient(
                        rs.getInt("PatientID"),
                        rs.getString("Name"),
                        rs.getString("ContactDetails"),
                        rs.getString("PersonalInformation")
                ));
            } else {
                return Either.left(Constantes.PATIENTDBERROR + "No patient found with id " + id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    @Override
    public boolean checkLogin(Credential p) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.AUTHENTICATION_QUERY)) {
            preparedStatement.setString(1, p.username());
            preparedStatement.setString(2, p.password());
            ResultSet credentialsResult = preparedStatement.executeQuery();
            if (!credentialsResult.next()) {
                return false;
            } else {
                return credentialsResult.getBoolean("isAuthenticated");
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public Either<String, Boolean> isPatientType(String username) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.ISTYPEPATIENTFROMUSERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            ResultSet credentialsResult = preparedStatement.executeQuery();
            if (!credentialsResult.next()) {
                return Either.left("No credentials found for the username " + username);
            } else {
                return Either.right(credentialsResult.getBoolean("isTypePatient"));
            }
        } catch (SQLException e) {
            return Either.left("Error in the database: " + e.getMessage());
        }
    }

    @Override
    public int save(Patient p) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getPatientDataFile()), ('\n' + p.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Patient initialpatient, Patient modifiedpatient) {
        delete(initialpatient);
        return 1;
    }

    @Override
    public int delete(Patient p) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.PATIENT_DELETE);
             PreparedStatement preparedStatement2 = con.prepareStatement(SQLConstants.CREDENTIALS_DELETEBYPATIENTID)) {
            preparedStatement2.setInt(1, p.getPatientID());
            int affectedRows2 = preparedStatement2.executeUpdate();
            if (affectedRows2 == 0) {
                throw new SQLException("Deleting patient credentials failed, no rows affected.");
            } else {
                preparedStatement.setInt(1, p.getPatientID());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting patient failed, no rows affected.");
                }
                return 1;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    public int delete(int patientID) {
        Connection con = null;
        try {
            con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.PATIENT_DELETE);
            con.setAutoCommit(false); // Start a transaction
            preparedStatement.setInt(1, patientID);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return -1; // No rows affected, return -1
            } else {
                con.commit();
                return 1; // Successful deletion, return 1
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an exception during deletion
                } catch (SQLException ex) {
                    log.error("Error during rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            return -1;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Restore auto-commit mode
                    con.close(); // Close the connection
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public int deletePatientWithCosas(int patientID) {
        Connection con = null;
        try {
            con = db.getConnection();
            PreparedStatement deletePayments = con.prepareStatement(SQLConstants.DELETEPAYMENTSFROMPATIENTID_QUERY);
            PreparedStatement deleteMedications = con.prepareStatement(SQLConstants.DELETEMEDICATIONSFROMPATIENTID_QUERY);
            PreparedStatement deleteRecords = con.prepareStatement(SQLConstants.RECORD_DELETEBYPATIENTID);
            PreparedStatement deleteAppointments = con.prepareStatement(SQLConstants.APP_DELETEBYPATIENTID);
            PreparedStatement deleteCredentials = con.prepareStatement(SQLConstants.CREDENTIALS_DELETEBYPATIENTID);
            PreparedStatement deletePatient = con.prepareStatement(SQLConstants.PATIENT_DELETE);

            con.setAutoCommit(false); // Start a transaction

            deletePayments.setInt(1, patientID);
            deleteMedications.setInt(1, patientID);
            deleteRecords.setInt(1, patientID);
            deleteAppointments.setInt(1, patientID);
            deleteCredentials.setInt(1, patientID);
            deletePatient.setInt(1, patientID);


            // Execute the delete statements
            deletePayments.executeUpdate();
            deleteMedications.executeUpdate();
            deleteRecords.executeUpdate();
            deleteAppointments.executeUpdate();
            deleteCredentials.executeUpdate();
            deletePatient.executeUpdate();

            con.commit(); // Commit the transaction
            return 1; // Successful deletion, return 1
        } catch (SQLException ex) {
            log.error("Error deleting the patient with stuff assigned: " + ex.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an exception during deletion
                } catch (SQLException e) {
                    log.error("Error during rollback: " + e.getMessage());
                }
            }
            return -1;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Restore auto-commit mode
                    con.close(); // Close the connection
                } catch (SQLException e) {
                    log.error("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    /*
    @Override
    public Either<String, List<Patient>> getAll() {
        List<Patient> patients = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getPatientDataFile()));
            for (String line : lines.subList(1, lines.size())) {
//                patients.add(new Patient(line, "withCredentials"));
                patients.add(new Patient(line));
            }
            return Either.right(patients);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }
    */
}