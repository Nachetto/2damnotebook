package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.AppointmentDao;
import org.example.dao.common.DBConnection;
import org.example.dao.common.SQLConstants;
import org.example.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao {
    private final DBConnection db;

    @Inject
    public AppointmentDaoImpl(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<String, List<Appointment>> getAll() {
        return null;
    }

    @Override
    public int save(Appointment c) {
        return 0;
    }

    @Override
    public int modify(Appointment c, Appointment cu) {
        return 0;
    }

    @Override
    public int delete(Appointment c) {
        return 0;
    }

    public int delete(int patientID) {
        try (var con = db.getConnection();
             var stmt = con.createStatement()) {
            return stmt.executeUpdate("DELETE FROM Appointment WHERE PatientID = " + patientID);
        } catch (Exception e) {
            return 0;
        }
    }

    public Either<String, LocalDateTime> getDateWithMorePatients() {
        try (var con = db.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery(SQLConstants.GETDATEWITHMOREPATIENTS_QUERY)) {
            if (rs.next()) {
                return Either.right(rs.getTimestamp("Date").toLocalDateTime());
            }
            return Either.left("No date found");
        } catch (Exception e) {
            return Either.left(e.getMessage());
        }
    }

    public boolean patientHasAppointments(int id) {
        try (var con = db.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM Appointment WHERE PatientID = " + id)) {
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
