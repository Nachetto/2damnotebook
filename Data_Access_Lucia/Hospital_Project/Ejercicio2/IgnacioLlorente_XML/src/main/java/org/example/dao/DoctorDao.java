package org.example.dao;

import io.vavr.control.Either;
import org.example.domain.Doctor;

import java.util.List;

public interface DoctorDao {

    Either<String, List<Doctor>> getAll();

    int save(Doctor c);

    int modify(Doctor c, Doctor cu);

    int delete(Doctor c);
}
