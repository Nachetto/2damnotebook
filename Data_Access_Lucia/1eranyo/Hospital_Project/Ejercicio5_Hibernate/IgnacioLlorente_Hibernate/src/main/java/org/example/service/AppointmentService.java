package org.example.service;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.AppointmentDaoImpl;

import java.time.LocalDateTime;

public class AppointmentService {
    private final AppointmentDaoImpl appointmentDao;

    @Inject
    public AppointmentService(AppointmentDaoImpl appointmentDao){
        this.appointmentDao = appointmentDao;
    }

    public Either<String, LocalDateTime> getDateWithMorePatients() {
        return appointmentDao.getDateWithMorePatients();
    }

    public boolean patientHasAppointments(int id) {
        return appointmentDao.patientHasAppointments(id);
    }

    public int delete(int patientID) {
        return appointmentDao.delete(patientID);
    }
}
