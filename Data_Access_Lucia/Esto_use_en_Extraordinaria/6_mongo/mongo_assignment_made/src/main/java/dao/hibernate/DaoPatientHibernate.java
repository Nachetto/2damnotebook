package dao.hibernate;

import io.vavr.control.Either;
import model.hibernate.PatientEntity;
import model.error.AppError;

import java.util.List;

public interface DaoPatientHibernate {
    Either<AppError, List<PatientEntity>> getAll();
}
