package com.hospitalcrud.dao.repository.staticDAO;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.domain.error.BadRequestException;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.error.NotFoundException;
import com.hospitalcrud.service.MedRecordService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements PatientDAO {
    private static List<Patient> patients = new ArrayList<>(List.of(
            new Patient(1, "Juan", LocalDate.of(1990,10,28), "1234567890"),
            new Patient(2, "Maria", LocalDate.of(1995,5,15), "0987654321"),
            new Patient(3, "Pedro", LocalDate.of(1980,3,10), "1230984567"),
            new Patient(4, "Ana", LocalDate.of(1975,12,25), "0981234567"),
            new Patient(5, "Jose", LocalDate.of(2000,1,1), "1234567890")
    ));

    @Override
    public List<Patient> getAll() {
        return patients;
    }

    @Override
    public int save(Patient m) {
        if (m == null) {
            throw new BadRequestException("Patient cannot be null");
        }
        try {
            patients.add(new Patient(patients.size() + 1, m.getName(), m.getBirthDate(), m.getPhone()));
            return patients.size();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving patient");
        }
    }

    @Override
    public void update(Patient m) {
        if (m == null) {
            throw new BadRequestException("Patient cannot be null");
        }
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId() == m.getId()) {
                patients.set(i, m);
                return;
            }
        }
        throw new NotFoundException("Patient with id " + m.getId() + " not found");
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) {//esto esta fatal, pero no voy a tocas el javascript del cliente
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getId() == id) {
                    patients.remove(i);
                    return true;
                }
            }
        }
        throw new NotFoundException("Patient with id " + id + " not found");
    }
}