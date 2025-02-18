package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.MedRecord;

import java.util.List;

public interface MedRecordDAO {
    List<MedRecord> getAll();
    List<MedRecord> get(int patientId);
    int save(MedRecord m);
    void update(MedRecord m);
    boolean delete(int id);

}
