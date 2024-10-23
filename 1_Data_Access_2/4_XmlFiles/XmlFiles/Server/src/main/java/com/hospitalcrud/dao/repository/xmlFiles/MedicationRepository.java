package com.hospitalcrud.dao.repository.xmlFiles;

import com.hospitalcrud.common.config.Configuration;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("xml")
@Log4j2
public class MedicationRepository implements MedicationDAO {
    //NOT IMPLEMENTED, they are on the medRecord xml file
    private final Configuration config;

    public MedicationRepository() {
        this.config = Configuration.getInstance();
    }
    //NOT IMPLEMENTED
    @Override
    public List<Medication> getAll() {
        return List.of();
    }

    @Override
    public int save(Medication m) {
        return 0;
    }

    @Override
    public void update(Medication m) {
        // dawdawdawd awdawd a dawdada
    }

    @Override
    public boolean delete(int id, boolean confirmation) {

        return false;
    }

}
