package com.hospitalcrud.dao.mappers.jdbc_mappers;

import com.hospitalcrud.dao.model.Patient;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapPatients {
    public List<Patient> readRS(ResultSet pacientes) {
        List<Patient> patients = new ArrayList<>();
        try {
            while (pacientes.next()) {
                //asignas a cada variable de tu objeto el nombre exacto de la columna en la base de datos
                patients.add(new Patient(
                        pacientes.getInt("patient_id"),
                        pacientes.getString("name"),
                        pacientes.getDate("date_of_birth").toLocalDate(),
                        pacientes.getString("phone"),
                        0
                ));
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

