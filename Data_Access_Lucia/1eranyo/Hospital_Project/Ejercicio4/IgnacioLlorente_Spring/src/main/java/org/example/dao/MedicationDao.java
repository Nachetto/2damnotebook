package org.example.dao;

import io.vavr.control.Either;
import org.example.domain.PrescribedMedication;

import java.util.List;

public interface MedicationDao {

    Either<String, List<PrescribedMedication>> getAll();

    int save(PrescribedMedication c);

    int modify( PrescribedMedication cu);

    int delete(PrescribedMedication c);
}
