package com.hospitalcrud.dao.repository.xmlFiles;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("xml")
public class MedicationRepository implements MedicationDAO {
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
