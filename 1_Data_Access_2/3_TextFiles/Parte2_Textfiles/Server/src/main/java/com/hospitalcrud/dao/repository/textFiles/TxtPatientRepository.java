package com.hospitalcrud.dao.repository.textFiles;

import com.hospitalcrud.common.config.Configuration;
import com.hospitalcrud.dao.mappers.PatientRowMapper;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

@Repository
@Profile("txt")
public class TxtPatientRepository implements PatientDAO {
    private final Configuration config;
    private final PatientRowMapper rowMapper;
    public TxtPatientRepository(PatientRowMapper rowMapper) {
        this.config = Configuration.getInstance();
        this.rowMapper = rowMapper;
    }

    // Metodo que usa el rowmapper
    private Patient mapToPatient(String line) {
        return rowMapper.mapRow(line);
    }

    // Metodo que lee el archivo de pacientes
    private List<String> readFile() {
        List<String> lines = new ArrayList<>();
        Path filePath = Paths.get(config.getPathPatients()); // Este path esta en la configuracion
        // Se lee con bufferedReader en este caso porque es mas eficiente para archivos grandes
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("Error reading the patient file " + e);
        }
        return lines;
    }

    @Override
    public List<Patient> getAll() {
        List<String> lines = readFile();
        List<Patient> patients = new ArrayList<>();

        for (String line : lines) {
            patients.add(mapToPatient(line));
        }
        return patients;
    }


    @Override
    public int save(Patient m) {
        int nextIdPatient= Integer.parseInt(config.getNextIdPatient());
        Path file = Paths.get(config.getPathPatients());
        if (!file.toFile().exists()) {
            throw new InternalServerErrorException("Error saving patient");
        } else {
            try (BufferedWriter bw = Files.newBufferedWriter(file, APPEND)) {
                bw.write(new Patient(nextIdPatient , m.getName(), m.getBirthDate(), m.getPhone()).toString());
                bw.newLine();
                config.setNextIdPatient(String.valueOf(nextIdPatient+1));
                return 1;
            } catch (IOException e) {
                throw new InternalServerErrorException("Error saving patient");
            }
        }
    }


    @Override
    public void update(Patient updatedPatient) {
        Path filePath = Paths.get(config.getPathPatients());
        List<String> newLines;

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            newLines = reader.lines()
                    .map(line -> {
                        Patient patient = mapToPatient(line);
                        if (patient.getId() == updatedPatient.getId()) {
                            return updatedPatient.toString();
                        } else {
                            return line;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new InternalServerErrorException("Error reading the patient file " + e);
        }

        try {
            Files.write(filePath, newLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error updating the patient file " + e);
        }
    }


    @Override
    public boolean delete(int patientId, boolean confirmation) {
        List<Patient> patients = getAll();
        Path filePath = Paths.get(config.getPathPatients());

        try {
            List<String> newLines = new ArrayList<>();
            for (Patient patient : patients) {
                if (patient.getId() != patientId) {
                    newLines.add(patient.toString());
                }
            }
            Files.write(filePath, newLines);
            return true;
        } catch (IOException e) {
            throw new InternalServerErrorException("Error deleting the patient " + e);
        }
    }
}
