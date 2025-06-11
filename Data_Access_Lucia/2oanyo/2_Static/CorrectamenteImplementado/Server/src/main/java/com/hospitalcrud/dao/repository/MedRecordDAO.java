package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.MedRecord;

import java.util.List;

public interface MedRecordDAO {
    List<MedRecord> getAll();
    int save(MedRecord m);
    void update(MedRecord m);
    void delete(int id, boolean confirmation);
}
