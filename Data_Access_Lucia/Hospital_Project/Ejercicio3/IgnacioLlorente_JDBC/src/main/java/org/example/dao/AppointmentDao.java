package org.example.dao;

import io.vavr.control.Either;
import org.example.domain.Appointment;

import java.util.List;

public interface AppointmentDao {

    Either<String, List<Appointment>> getAll();

    int save(Appointment c);

    int modify(Appointment c, Appointment cu);

    int delete(Appointment c);
}
