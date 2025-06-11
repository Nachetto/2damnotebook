package com.hospitalcrud.dao.repository.textFiles;

import com.hospitalcrud.common.config.Configuration;
import com.hospitalcrud.dao.mappers.DoctorRowMapper;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

@Repository
@Profile("txt")
public class TxtDoctorRepository implements DoctorDAO {
    private final Configuration config;
    private final DoctorRowMapper rowMapper;

    public TxtDoctorRepository(DoctorRowMapper doctorRowMapper) {
        this.config = Configuration.getInstance();
        this.rowMapper = doctorRowMapper;
    }

    private void writeFile(List<String> lines) {
        try {
            Files.write(Paths.get(config.getPathDoctors()), lines);
        } catch (IOException e) {
            System.err.println("Error escribiendo en el archivo: " + e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAll() {
        List<String> lines = readFile();//devuelve una lista de strings que se la paso al rowmapper
        return parseDoctors(lines);
    }
    private List<String> readFile() {
        try { // Se lee con Files.readAllLines porque es mas sencillo y es un archivo pequeño, si fuera grande se usaria bufferedReader
            return Files.readAllLines(Paths.get(config.getPathDoctors())); // java.nio.file.Files;
        } catch (IOException e) {
            throw new InternalServerErrorException("Error leyendo el archivo: " + e.getMessage());
        }
    }
    //private final DoctorRowMapper rowMapper;
    private List<Doctor> parseDoctors(List<String> lines) { // Mas de lo mismo, se usa el rowmapper para parsear las lineas
        return lines.stream()
                .map(rowMapper::mapRow)
                .toList();
    }


    @Override
    public int save(Doctor d) {
        int nextIdDoctor= Integer.parseInt(config.getNextIdDoctor());

        Path filePath = Paths.get(config.getPathDoctors());
        if (!filePath.toFile().exists()) {
            throw new InternalServerErrorException("Error saving doctor");
        } else {
            try (BufferedWriter bw = Files.newBufferedWriter(filePath, APPEND /*Para no cargarte lo anterior*/)) {
                bw.write(new Doctor(nextIdDoctor,d.getName(), d.getSpecialty()).toString());
                bw.newLine();
                config.setNextIdDoctor(String.valueOf(nextIdDoctor+1));
                return 1;
            } catch (IOException e) {
                throw new InternalServerErrorException("Error saving doctor");
            }
        }
    }


    @Override
    public void update(Doctor doctor) {
        List<String> lines = readFile();

        List<String> updatedLines = lines.stream()
                .map(line -> {
                    Doctor currentDoctor = rowMapper.mapRow(line);
                    if (currentDoctor != null && currentDoctor.getId() == doctor.getId()) {
                        throw new InternalServerErrorException("Error updating doctor");
                    }
                    return line;
                })
                .collect(Collectors.toList());

        writeFile(updatedLines);
    }


    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) {
            System.out.println("Eliminación no confirmada.");
            return false;
        }

        List<String> lines = readFile();

        List<String> updatedLines = lines.stream()
                .filter(line -> {
                    Doctor currentDoctor = rowMapper.mapRow(line);
                    return currentDoctor == null || currentDoctor.getId() != id;
                })
                .collect(Collectors.toList());

        writeFile(updatedLines);
        return true;
    }

}
