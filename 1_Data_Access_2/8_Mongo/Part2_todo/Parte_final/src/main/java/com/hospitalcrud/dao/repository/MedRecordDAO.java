package com.hospitalcrud.dao.repository;

import com.hospitalcrud.dao.model.MedRecord;
import org.bson.types.ObjectId;

import java.util.List;

public interface MedRecordDAO {
    List<MedRecord> getAll();
    List<MedRecord> get(ObjectId patientId);
    int save(MedRecord m);
    void update(MedRecord m);
    boolean delete(ObjectId id);

}
