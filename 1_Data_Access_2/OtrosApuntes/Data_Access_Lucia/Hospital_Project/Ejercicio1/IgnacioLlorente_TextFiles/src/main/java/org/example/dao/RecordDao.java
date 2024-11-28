package org.example.dao;

import io.vavr.control.Either;
import org.example.domain.Record;

import java.util.List;

public interface RecordDao {

    Either<String, List<Record>> getAll();

    int save(Record c);

    int modify(Record c, Record cu);

    int delete(Record c);
}
