package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.RecordDaoImpl;
import org.example.domain.Record;

import java.util.List;

public class RecordService {
    private final RecordDaoImpl recordDao;
    @Inject
    public RecordService(RecordDaoImpl recordDao) {
        this.recordDao = recordDao;
    }

    public Either<String, List<Record>> getAll() {
        return recordDao.getAll();
    }

    public Either<String, Record> get(int id) {
        return recordDao.get(id);
    }

    public int save(Record r) {
        return recordDao.save(r);
    }

    public int modify(Record initialrecord, Record modifiedrecord) {
        return recordDao.modify(initialrecord, modifiedrecord);
    }

    public int delete(Record r) {
        return recordDao.delete(r);
    }

    public int deleteByPatient(int id) {
        return recordDao.deleteByPatient(id);
    }

    public boolean hasMedications(int id, MedicationService medicationService) {
        return recordDao.hasMedications(id,  medicationService);
    }
}
