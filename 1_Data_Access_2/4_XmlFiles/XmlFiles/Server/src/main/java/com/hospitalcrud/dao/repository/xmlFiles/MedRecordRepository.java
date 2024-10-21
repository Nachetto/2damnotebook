package com.hospitalcrud.dao.repository.xmlFiles;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("xml")
public class MedRecordRepository implements MedRecordDAO {
    @Override
    public List<MedRecord> getAll() {
        //devuelve tambien las medicaciones de cada medrecord
        return List.of();
    }

    @Override
    public List<MedRecord> get(int patientId) {
        return List.of();
    }

    @Override
    public int save(MedRecord m) {
        return 0;
    }

    @Override
    public void update(MedRecord m) {
        //dadwaw awdawdadada awdad
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
