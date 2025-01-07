package com.hospitalcrud.dao.respositories.jdbc;

import com.hospitalcrud.common.Constantes;
import com.hospitalcrud.dao.mappers.jdbc_mappers.MapMedicalRecords;
import com.hospitalcrud.dao.model.MedicalRecord;
import com.hospitalcrud.dao.respositories.MedicalRecordsRepository;
import com.hospitalcrud.dao.utilities.DBConnectionPool;
import com.hospitalcrud.dao.utilities.SQLQueries;
import com.hospitalcrud.domain.error.FOREIGN_KEY_ERROR;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("jdbc")
public class JDBCMedicalRecordsRepository implements MedicalRecordsRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final MapMedicalRecords medicalRecordsMapper;
    private final DBConnectionPool pool;

    public JDBCMedicalRecordsRepository(MapMedicalRecords medicalRecordsMapper, DBConnectionPool pool) {
        this.medicalRecordsMapper = medicalRecordsMapper;
        this.pool = pool;
    }

    @Override
    public List<MedicalRecord> getAll(int idPatient) {
        try (Connection con = pool.getConnection();
             PreparedStatement getMedicalRecords = con.prepareStatement(SQLQueries.GET_MEDICAL_RECORDS)) {
            getMedicalRecords.setInt(1, idPatient);
            return medicalRecordsMapper.readRS(getMedicalRecords.executeQuery());
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        try (Connection conn = pool.getConnection();
             PreparedStatement deleteMedicalRecord = conn.prepareStatement(SQLQueries.DELETE_MEDICAL_RECORD);
             PreparedStatement deletePrescribedMedications = conn.prepareStatement(SQLQueries.DELETE_PRESCRIBED_MEDICATIONS)) {
            try {
                conn.setAutoCommit(false);
                deletePrescribedMedications.setInt(1, medicalRecord.getId());
                deletePrescribedMedications.executeUpdate();
                deleteMedicalRecord.setInt(1, medicalRecord.getId());
                deleteMedicalRecord.executeUpdate();
                conn.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                throw new FOREIGN_KEY_ERROR();
            } catch (SQLException e) {
                conn.rollback();
            }
        } catch (SQLException e) {
           throw new RuntimeException();
        }
    }

    @Override
    public int save(MedicalRecord medicalRecord) {
        try (Connection conn = pool.getConnection();
             PreparedStatement addMedicalRecord = conn.prepareStatement(SQLQueries.INSERT_MEDICAL_RECORD, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement addPrescribedMedications = conn.prepareStatement(SQLQueries.INSERT_MEDICATION);
        ) {
            try {
                conn.setAutoCommit(false);
                addMedicalRecord.setInt(1, medicalRecord.getIdPatient());
                addMedicalRecord.setInt(2, medicalRecord.getIdDoctor());
                addMedicalRecord.setString(3, medicalRecord.getDiagnosis());
                addMedicalRecord.setDate(4, Date.valueOf(medicalRecord.getDate().toString()));
                addMedicalRecord.executeUpdate();
                ResultSet rs = addMedicalRecord.getGeneratedKeys();
                int medicalRecordId;
                rs.next();
                medicalRecordId = rs.getInt(1);
                medicalRecord.setId(medicalRecordId);
                addMedications(addPrescribedMedications, medicalRecord);
                conn.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new FOREIGN_KEY_ERROR();
            } catch (SQLException e) {
                conn.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return medicalRecord.getId();
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        try (Connection conn = pool.getConnection();
             PreparedStatement updateMedicalRecord = conn.prepareStatement(SQLQueries.UPDATE_MEDICAL_RECORD);
             PreparedStatement deletePrescribedMedications = conn.prepareStatement(SQLQueries.DELETE_PRESCRIBED_MEDICATIONS);
             PreparedStatement addPrescribedMedications = conn.prepareStatement(SQLQueries.INSERT_MEDICATION);
        ) {
            try {
                conn.setAutoCommit(false);
                deletePrescribedMedications.setInt(1, medicalRecord.getId());
                deletePrescribedMedications.executeUpdate();
                if (!medicalRecord.getMedications().isEmpty())
                    addMedications(addPrescribedMedications, medicalRecord);
                updateMedicalRecord.setInt(1, medicalRecord.getIdDoctor());
                updateMedicalRecord.setString(2, medicalRecord.getDiagnosis());
                updateMedicalRecord.setDate(3, Date.valueOf(medicalRecord.getDate().toString()));
                updateMedicalRecord.setInt(4, medicalRecord.getId());
                updateMedicalRecord.executeUpdate();
                conn.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new FOREIGN_KEY_ERROR();
            } catch (SQLException e) {
                conn.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    private void addMedications(PreparedStatement addPrescribedMedications, MedicalRecord medicalRecord) throws SQLException {
        medicalRecord.getMedications().forEach(m -> {
            try {
                addPrescribedMedications.setInt(1, medicalRecord.getId());
                addPrescribedMedications.setString(2, m.getMedicationName());
                addPrescribedMedications.setString(3, m.getDosage());
                addPrescribedMedications.addBatch();
            } catch (SQLException e) {
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        });
        addPrescribedMedications.executeBatch();
    }
}
