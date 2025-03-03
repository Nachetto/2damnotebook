package com.hospitalcrud.dao.respositories.jdbc;

import com.hospitalcrud.common.Constantes;
import com.hospitalcrud.dao.mappers.jdbc_mappers.MapPatients;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.respositories.PatientRepository;
import com.hospitalcrud.dao.utilities.DBConnectionPool;
import com.hospitalcrud.dao.utilities.SQLQueries;
import com.hospitalcrud.domain.error.DUPLICATED_USERNAME;
import com.hospitalcrud.domain.error.FOREIGN_KEY_ERROR;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Profile("jdbc")
@Repository
public class JDBCPatientsRepository implements PatientRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final MapPatients patientsMapper;
    private final DBConnectionPool pool;

    public JDBCPatientsRepository(MapPatients patientsMapper, DBConnectionPool pool) {
        this.patientsMapper = patientsMapper;
        this.pool = pool;
    }


    @Override
    public List<Patient> getAll() {
        try (Connection con = pool.getConnection();
             Statement getPatients = con.createStatement();
        ) {
            ResultSet resultSet = getPatients.executeQuery(SQLQueries.GET_ALL_PATIENTS);
            return patientsMapper.readRS(resultSet);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public int save(Patient patient) {
        try (Connection con = pool.getConnection();
             PreparedStatement insertPatient = con.prepareStatement(SQLQueries.INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertCredential = con.prepareStatement(SQLQueries.INSERT_CREDENTIAL)
        ) {
            try {con.setAutoCommit(false);
                setPatientValues(patient, insertPatient).executeUpdate();
                ResultSet rs = insertPatient.getGeneratedKeys();
                rs.next();
                //(username,password,patient_id,doctor_id)
                insertCredential.setString(1, patient.getCredential().getUserName());
                insertCredential.setString(2, patient.getCredential().getPassword());
                //el patientid esta en la posicion 1 del ResultSet por llamar a getGeneratedKeys():
                insertCredential.setInt(3, rs.getInt(1));
                insertCredential.setNull(4, 0);
                insertCredential.executeUpdate();
                con.commit();
                patient.setId(rs.getInt(1));
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new DUPLICATED_USERNAME();
            } catch (SQLException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return patient.getId();
    }

    @Override
    public void update(Patient patient) {
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_PATIENT)
        ) {
            setPatientValues(patient, preparedStatement).executeUpdate();
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
        }
    }
    private PreparedStatement setPatientValues(Patient patient, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setDate(2, Date.valueOf(patient.getBirthDate()));
        preparedStatement.setString(3, patient.getPhone());
        preparedStatement.setInt(4, patient.getId());
        return preparedStatement;
    }

    @Override
    public boolean delete(int patientId, boolean confirmation) {
        int result = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement deletePatient = con.prepareStatement(SQLQueries.DELETE_PATIENT);
             PreparedStatement deleteCredential = con.prepareStatement(SQLQueries.DELETE_CREDENTIAL);
             PreparedStatement deleteMedicalRecords = con.prepareStatement(SQLQueries.DELETE_PATIENT_MEDICAL_RECORDS);
             PreparedStatement deletePrescribedMedications = con.prepareStatement(SQLQueries.DELETE_PATIENT_PRESCRIBED_MEDICATIONS);
             PreparedStatement deletePatientPayments = con.prepareStatement(SQLQueries.DELETE_PATIENT_PAYMENTS);
             PreparedStatement deletePatientAppointments = con.prepareStatement(SQLQueries.DELETE_PATIENT_APPOINTMENTS);
        ) {
            try {
                con.setAutoCommit(false);
                if (confirmation) {
                    deletePrescribedMedications.setInt(1, patientId);
                    deletePrescribedMedications.executeUpdate();

                    deleteMedicalRecords.setInt(1, patientId);
                    deleteMedicalRecords.executeUpdate();

                    deletePatientPayments.setInt(1, patientId);
                    deletePatientPayments.executeUpdate();

                    deletePatientAppointments.setInt(1,patientId);
                    deletePatientAppointments.executeUpdate();
                }
                deleteCredential.setInt(1, patientId);
                deleteCredential.executeUpdate();

                deletePatient.setInt(1, patientId);
                result = deletePatient.executeUpdate();
                con.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new FOREIGN_KEY_ERROR();
            } catch (SQLException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
        }
        return result == 1;
    }


}
