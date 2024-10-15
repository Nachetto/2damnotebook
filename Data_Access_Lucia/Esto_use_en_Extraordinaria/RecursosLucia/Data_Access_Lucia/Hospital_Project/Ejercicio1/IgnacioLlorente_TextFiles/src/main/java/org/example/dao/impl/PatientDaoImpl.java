package org.example.dao.impl;

import io.vavr.control.Either;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.PatientDao;
import org.example.domain.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.example.domain.Credential;


public class PatientDaoImpl implements PatientDao {
    @Override
    public Either<String, List<Patient>> getAll() {
        List<Patient> patients = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getPatientDataFile()));
            for (String line : lines.subList(1, lines.size())) {
//                patients.add(new Patient(line, "withCredentials"));
                patients.add(new Patient(line));
            }
            return Either.right(patients);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }
    
    public Either<String, Patient> get(int id) {
        List<Patient> list= getAll().get().stream().filter(p -> p.getPatientID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public boolean checkLogin(Credential p) {
        //getting the password from the passwords.xml file
        return Configuration.getInstance().getPassword(
                p.username()).equals(p.password()
        );

        /* The right way to implement this method is to use the following code:
        Either<String, List<Patient>> result = getAll();

        if (result.isRight()) {
            for (Patient pat : result.get()) {
                if (Objects.equals(pat.getCredential(), p)) {
                    return true;
                }
            }
        }
        else {//todo exception handling
            System.out.println(result.getLeft());
        }
        return false;
        */
    }

    @Override
    public int save(Patient p) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getPatientDataFile()), ('\n' + p.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Patient initialpatient, Patient modifiedpatient) {
        delete(initialpatient);
        return 1;
    }

    @Override
    public int delete(Patient p) {
        if (p == null) {
            return -1;
        }
        try {
            List<Patient> patients = getAll().get();
            patients.remove(p);
            Files.write(Paths.get(Configuration.getInstance().getPatientDataFile()), "patientID;name;contactDetails;personalInformation;username;password".getBytes());
            for (Patient patient : patients) {
                save(patient);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }


    public int delete(int patientID) {
        List<Patient> patients = getAll().get();
        Patient patient = patients.stream().filter(p -> p.getPatientID() == patientID).findFirst().get();
        return delete(patient);
    }
}
