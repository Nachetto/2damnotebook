package com.hospitalcrud.dao.repository.spring;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.model.rowmappers.MedicationRowMapper;
import com.hospitalcrud.dao.repository.MedicationDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("spring")
@Log4j2
public class MedicationRepository implements MedicationDAO {

    private final JdbcClient jdbcClient;

    public MedicationRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public int save(Medication m) {
        try {
            String sql = "INSERT INTO prescribed_medications (record_id, medication_name, dosage) VALUES (?,?,null)";
            return jdbcClient.sql(sql)
                    .param(1, m.getMedRecordId())
                    .param(2, m.getMedicationName())
                    .update();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void update(Medication m) {
        try {
            String sql = "UPDATE prescribed_medications SET medication_name = ? WHERE prescription_id = ?";
            jdbcClient.sql(sql)
                    .param(1, m.getMedicationName())
                    .param(2, m.getId())
                    .update();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            String sql = "DELETE FROM prescribed_medications WHERE record_id = ?";
            return jdbcClient.sql(sql)
                    .param(1, id)
                    .update() > 0;
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while deleting all medications from the record, cause: " + e.getMessage());
        }
    }

    @Override
    public List<Medication> get(int medRecordId) {
        try {
            return jdbcClient.sql("SELECT * FROM prescribed_medications WHERE record_id = ?")
                    .param(1, medRecordId)
                    .query(new MedicationRowMapper()).list();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while getting medications for record with id: " + medRecordId + ", cause: " + e.getMessage());
        }
    }

    public Medication getById(int id) {
        try {
            Optional<Medication> optionalMedication = jdbcClient.sql("SELECT * FROM prescribed_medications WHERE prescription_id = ?")
                    .param(1, id)
                    .query(new MedicationRowMapper())
                    .optional();

            return optionalMedication.orElse(null);
        } catch (Exception e) {
            log.error("Error fetching medication with id: {}", id, e);
            return null;
        }
    }




    //This method is not used in the project
    @Override
    public List<Medication> getAll() {
        return List.of();
    }

}
