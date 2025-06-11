package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.Constants;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Log4j2
public class MedicationRepository implements MedicationDAO {
    private final DBConnection db;

    public MedicationRepository(DBConnection db) {
        this.db = db;
    }

    @Override
    public List<Medication> get(int medRecordId) {// GET BY RECORD ID
        try(Connection con = db.getHikariDataSource().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(Constants.GET_BY_RECORDID)) {
            preparedStatement.setInt(1, medRecordId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Medication> medications = new ArrayList<>();
            while (rs.next()) {
                medications.add(new Medication(
                        rs.getInt("prescription_id"),
                        rs.getString("medication_name"),
                        rs.getInt("record_id")
                ));
            }
            return medications;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while getting medications for record with id: " + medRecordId + ", cause: " + e.getMessage());
        }
    }

    @Override
    public int save(Medication m) {
        try (Connection con = db.getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.SAVE_MEDICATION)) {
            preparedStatement.setInt(1, m.getMedRecordId());
            preparedStatement.setString(2, m.getMedicationName());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void update(Medication m) {
        try(Connection con = db.getHikariDataSource().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(Constants.UPDATE_MEDICATION)) {
            preparedStatement.setString(1, m.getMedicationName());
            preparedStatement.setInt(2, m.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new InternalServerErrorException("Error while updating medication with id: " + m.getId());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try(Connection con = db.getHikariDataSource().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(Constants.DELETE_MEDICATION)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while deleting all medications from the record, cause: " + e.getMessage());
        }
    }




    //This method is not used in the project
    @Override
    public List<Medication> getAll() {
        return List.of();
    }
}
