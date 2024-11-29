package com.hospitalcrud.dao.respositories.jdbc;

import com.hospitalcrud.common.Constantes;
import com.hospitalcrud.dao.mappers.jdbc_mappers.MapMedications;
import com.hospitalcrud.dao.model.MedicalRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.respositories.MedicationsRepository;
import com.hospitalcrud.dao.utilities.DBConnectionPool;
import com.hospitalcrud.dao.utilities.SQLQueries;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Profile("jdbc")
@Repository
public class JDBCMedicationsRepository implements MedicationsRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool pool;
    private final MapMedications medicationsMapper;

    public JDBCMedicationsRepository(DBConnectionPool pool, MapMedications medicationsMapper) {
        this.pool = pool;
        this.medicationsMapper = medicationsMapper;
    }

    public List<Medication> getPrescribedMedications (int medicalRecordId) {
        try (Connection conn = pool.getConnection();
            PreparedStatement getPrescribedMedications = conn.prepareStatement(SQLQueries.GET_PRESCRIBED_MEDICATIONS);
        ) {
            getPrescribedMedications.setInt(1, medicalRecordId);
            return medicationsMapper.readRS(getPrescribedMedications.executeQuery());
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public List<Medication> getAll() {
        try (Connection conn = pool.getConnection();
        Statement stmt = conn.createStatement();
        ) {
            return medicationsMapper.readRS(stmt.executeQuery(SQLQueries.GET_ALL_MEDICATIONS));
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public void deletePatientMedications(int patientId) {

    }

    @Override
    public void deleteMedicalRecordMedications(int medicalRecordId) {

    }

    @Override
    public void save(MedicalRecord medicalRecord) {

    }

    @Override
    public void update(MedicalRecord medicalRecord) {

    }
}
