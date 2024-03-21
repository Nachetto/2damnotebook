package org.example.dao.impl;

import io.vavr.control.Either;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.RecordDao;
import org.example.domain.Record;
import org.example.service.MedicationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    @Override
    public Either<String, List<Record>> getAll() {
        List<Record> records = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getRecordDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                records.add(new Record(line));
            }
            return Either.right(records);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    public Either<String, Record> get(int id) {
        List<Record> list= getAll().get().stream().filter(r -> r.getRecordID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public int save(Record r) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), ('\n' + r.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Record initialrecord, Record modifiedrecord) {
        delete(initialrecord);
        return 1;
    }

    @Override
    public int delete(Record r) {
        if (r == null) {
            return -1;
        }
        try {
            List<Record> records = getAll().get();
            records.remove(r);
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), "".getBytes());
            for (Record record : records) {
                save(record);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public int deleteByPatient(int id) {
        try {
            List<Record> records = getAll().get();
            records.removeIf(r -> r.getPatientID() == id);
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), "".getBytes());
            for (Record record : records) {
                save(record);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public boolean hasMedications(int id, MedicationService medicationService) {
        return medicationService.getAll().get().stream()
                .anyMatch(m -> getAll().get().stream().anyMatch
                        (r -> r.getRecordID() == m.getRecordID()
                                && r.getPatientID() == id));
    }
}
