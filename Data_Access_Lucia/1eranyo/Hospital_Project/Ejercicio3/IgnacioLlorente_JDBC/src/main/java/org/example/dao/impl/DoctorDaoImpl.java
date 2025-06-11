package org.example.dao.impl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.DoctorDao;
import org.example.dao.common.DBConnection;
import org.example.domain.Doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.common.SQLConstants;

public class DoctorDaoImpl implements DoctorDao{
    private final DBConnection db;
    @Inject
    public DoctorDaoImpl(DBConnection db) {
        this.db = db;
    }
    @Override
    public Either<String, List<Doctor>> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getDoctorDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                doctors.add(new Doctor(line));
            }
            return Either.right(doctors);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    public Either<String, Doctor> get(int id) {
        List<Doctor> list= getAll().get().stream().filter(d -> d.getDoctorID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public int save(Doctor d) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getDoctorDataFile()), ('\n' + d.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Doctor initialdoctor, Doctor modifieddoctor) {
        delete(initialdoctor);
        return 1;
    }

    @Override
    public int delete(Doctor d) {
        if (d == null) {
            return -1;
        }
        try {
            List<Doctor> doctors = getAll().get();
            doctors.remove(d);
            Files.write(Paths.get(Configuration.getInstance().getDoctorDataFile()), "doctorID;name;specialty;contactDetails".getBytes());
            for (Doctor doctor : doctors) {
                save(doctor);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public int getDoctorIDFromUsername(String username) {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLConstants.GETDOCTORIDFROMUSERNAME_QUERY)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("PatientOrDoctorID");
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
