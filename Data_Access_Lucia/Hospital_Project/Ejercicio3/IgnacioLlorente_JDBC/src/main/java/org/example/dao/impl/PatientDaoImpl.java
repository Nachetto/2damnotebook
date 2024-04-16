package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.PatientDao;
import org.example.dao.common.DBConnection;
import org.example.dao.common.SQLConstants;
import org.example.domain.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.domain.Credential;

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
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.CREDENTIALSFROMPATIENTID_QUERY)) {

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

    public Either<String, Patient> get(int id) {
        List<Patient> list= getAll().get().stream().filter(p -> p.getPatientID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public boolean checkLogin(Credential p) {
        //getting the password from the passwords.xml file
        return Configuration.getInstance().getPassword(
                p.username()).equals(p.password()
        );

        /* The right way to implement this method is to use the following code:
        Either<String, List<Patient>> result = getAll();

        if (result.isRight()) {
            for (Patient pat : result.get()) {
                if (Objects.equals(pat.getCredential(), p)) {
                    return true;
                }
            }
        }
        else {//todo exception handling
            System.out.println(result.getLeft());
        }
        return false;
        */
    }

    public boolean isPatientType(Credential p) {
        return Configuration.getInstance().getPatientType(
                p.username()).equals("Patient"
        );
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
        if (p == null) {
            return -1;
        }
        try {
            List<Patient> patients = getAll().get();
            patients.remove(p);
            Files.write(Paths.get(Configuration.getInstance().getPatientDataFile()), "patientID;name;contactDetails;personalInformation;username;password".getBytes());
            for (Patient patient : patients) {
                save(patient);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }


    public int delete(int patientID) {
        List<Patient> patients = getAll().get();
        Patient patient = patients.stream().filter(p -> p.getPatientID() == patientID).findFirst().get();
        return delete(patient);
    }
}