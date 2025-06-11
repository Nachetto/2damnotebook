package org.example.dao;

import org.example.domain.Credential;
import org.example.domain.Patient;

import java.util.List;
import io.vavr.control.Either;

public interface PatientDao {

    Either<String, List<Patient>> getAll();

    int save(Patient c);

    int modify(Patient c, Patient cu);

    int delete(Patient c);

    boolean checkLogin(Credential c);
}
