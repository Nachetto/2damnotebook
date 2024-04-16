package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.MedicationDao;
import org.example.dao.PatientAdapter;
import org.example.dao.common.DBConnection;
import org.example.dao.common.SQLConstants;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.xml.MedicationXML;
import org.example.domain.xml.RecordXML;
import org.example.domain.xml.RecordsXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class MedicationDaoImpl implements MedicationDao {

    private final RecordDaoImpl recordDao;
    private final DBConnection db;

    @Inject
    public MedicationDaoImpl(RecordDaoImpl recordDao, DBConnection db) {
        this.recordDao = recordDao;
        this.db = db;
    }

    @Override
    public Either<String, List<PrescribedMedication>> getAll() {
        List<PrescribedMedication> medications = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getMedicationDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                medications.add(new PrescribedMedication(line));
            }
            return Either.right(medications);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    //select all the prescribed medication for a patient
    public Either<String, List<PrescribedMedication>> getAllByPatient(int patientID) throws SQLException {
        try (Connection con = db.getConnection();
        Statement stmt = con.createStatement();
        PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.MEDICATIONSBYPATIENTID_QUERY)) {
            preparedStatement.setInt(1, patientID);
            ResultSet rs = preparedStatement.executeQuery();
            List<PrescribedMedication> medications = new ArrayList<>();
            while (rs.next()) {
                medications.add(
                        new PrescribedMedication(rs.getInt("MedicationID"),
                                rs.getString("Name"),
                                rs.getString("Dosage"),
                                rs.getInt("RecordID")
                        )
                );
            }
            return Either.right(medications);
        } catch(SQLException e){
            log.error(e.getMessage());
            return Either.left(Constantes.MEDICATIONDBERROR + e.getMessage());
        }
    }

    public Either<String, PrescribedMedication> get(int id) {
        List<PrescribedMedication> list = getAll().get().stream().filter(m -> m.getMedicationID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public int save(PrescribedMedication m) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getMedicationDataFile()), ('\n' + m.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            //set the last medicationID property in the configuration file properties.txt
            Configuration.getInstance().setLastMedicationID(m.getMedicationID());
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(PrescribedMedication initialmedication, PrescribedMedication modifiedmedication) {
        delete(initialmedication);
        return 1;
    }

    @Override
    public int delete(PrescribedMedication m) {
        if (m == null) {
            return -1;
        }
        try {
            List<PrescribedMedication> medications = getAll().get();
            medications.remove(m);
            Files.write(Paths.get(Configuration.getInstance().getMedicationDataFile()), "".getBytes());
            for (PrescribedMedication medication : medications) {
                save(medication);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public int deleteByPatient(int id) {
        try {
            Either<String, List<PrescribedMedication>> medications = getAll();
            if (medications.isRight()) {
                List<PrescribedMedication> meds = medications.get();

                //save a list of record IDs to delete
                List<Integer> recordIDs = recordDao.getRecordIdsFromPatientId(id);

                meds.removeIf(medication -> recordIDs.contains(medication.getRecordID()));

                Files.write(Paths.get(Configuration.getInstance().getMedicationDataFile()), "medicationID;name;dosage;redordID".getBytes());
                for (PrescribedMedication medication : meds) {
                    save(medication);
                }
                return 1;
            } else {
                System.out.println(medications.getLeft());
                return -1;
            }
        } catch (IOException e) {
            return -1;
        }
    }

    public List<Patient> getPatientsMedicatedWith(String medicationName) {
        List<Patient> patients = new ArrayList<>();
        try {
            RecordsXML recordsXML = recordDao.readRecordsFromXML();
            if (recordsXML == null) {
                return patients;
            }
            for (RecordXML recordXML : recordsXML.getRecords()) {
                for (MedicationXML medication : recordXML.getMedications().getMedication()) {
                    if (medication.getName().equals(medicationName)) {
                        patients.add(new PatientAdapter().unmarshal(recordXML.getPatient()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return patients;
        }
        //removing duplicated patients
        patients.removeIf(patient -> patients.stream().anyMatch(p -> p.getPatientID() == patient.getPatientID()));
        return patients;
    }

    public int getNewMedicationID() {
        return Integer.parseInt(Configuration.getInstance().getLastMedicationID()) + 1;
    }
}
