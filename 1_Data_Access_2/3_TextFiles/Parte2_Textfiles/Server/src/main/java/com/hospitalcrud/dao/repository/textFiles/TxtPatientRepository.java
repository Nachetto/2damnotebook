package com.hospitalcrud.dao.repository.textFiles;

import com.hospitalcrud.common.config.Configuration;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import jakarta.inject.Inject;

import java.util.List;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class TxtPatientRepository implements PatientDAO {

    private final Configuration config;

    @Inject
    public TxtPatientRepository(Configuration config) {
        this.config = config;
    }


    @Override
    public List<Patient> getAll() {
        Path file = Paths.get(config.getPathPatients());
        List<Patient> patientList = new ArrayList<>();

        if (!file.toFile().exists()) {
            throw new InternalServerErrorException("File does not exist");
        }

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String st;
            StringBuilder malformedLine = new StringBuilder();

            // Check if the file is empty first
            if (Files.size(file) == 0) {
                throw new InternalServerErrorException("No data found");
            } else {
                // Read the file line by line and create a new Patient object for each line
                while ((st = br.readLine()) != null) {
                    if (!st.trim().isEmpty()) { // Skip empty lines
                        String[] parts = st.split(";");
                        if (parts.length == 4) {
                            Patient patient = new Patient(st);
                            patientList.add(patient);
                        } else {
                            malformedLine.append(st);
                            if (malformedLine.toString().split(";").length == 4) {
                                Patient patient = new Patient(malformedLine.toString());
                                patientList.add(patient);
                                malformedLine.setLength(0); // Clear the buffer
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Error reading file");
        }

        return patientList;
    }

    @Override
    public int save(Patient m) {
        Path file = Paths.get(config.getPathPatients());
        try (BufferedWriter bw = Files.newBufferedWriter(file, APPEND, TRUNCATE_EXISTING)) {
            bw.write(m.toString());
            bw.newLine();
            return 1;
        } catch (IOException e) {
            throw new InternalServerErrorException("Error saving patient");
        }
    }

    @Override
    public void update(Patient m) {

    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
