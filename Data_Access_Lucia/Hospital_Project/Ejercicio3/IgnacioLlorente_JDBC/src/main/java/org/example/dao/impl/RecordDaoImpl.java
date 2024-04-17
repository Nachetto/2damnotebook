package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.MedicationAdapter;
import org.example.dao.PatientAdapter;
import org.example.dao.RecordDao;
import org.example.dao.common.DBConnection;
import org.example.dao.common.SQLConstants;
import org.example.domain.Doctor;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.Record;
import org.example.domain.xml.MedicationsXML;
import org.example.domain.xml.PatientXML;
import org.example.domain.xml.RecordXML;
import org.example.domain.xml.RecordsXML;
import org.example.service.MedicationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class RecordDaoImpl implements RecordDao {

    private final DBConnection db;

    @Inject
    public RecordDaoImpl(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<String, List<Record>> getAll() {
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement()) {
            List<Record> records = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(SQLConstants.GETALLRECORDS_QUERY);
            while (rs.next()) {
                records.add(new Record(rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getString("Diagnosis"),
                        rs.getInt("DoctorID")
                ));
            }
            return Either.right(records);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }
    }

    public Either<String, Record> get(int id) {
        List<Record> list = getAll().get().stream().filter(r -> r.getRecordID() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constantes.PATIENTDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public int save(Record r) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.RECORD_INSERT)
        ) {
            preparedStatement.setInt(1, r.getPatientID());
            preparedStatement.setString(2, r.getDiagnosis());
            preparedStatement.setInt(3, r.getDoctorID());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(Record initialrecord, Record modifiedrecord) {
        delete(initialrecord);
        save(modifiedrecord);
        return 1;
    }

    @Override
    public int delete(Record r) {
        if (r == null) {
            return -1;
        }
        try {
            List<Record> records = getAll().get();
            records.remove(r);
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), "recordID;patientID;diagnosis;doctorID".getBytes());
            for (Record record : records) {
                save(record);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }


    public String medicationsFromARecordId(int recordID) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.MEDICATIONSFROMRECORDID_QUERY)
        ) {
            preparedStatement.setInt(1, recordID);
            ResultSet rs = preparedStatement.executeQuery();
            StringBuilder medications = new StringBuilder();
            while (rs.next()) {
                medications.append("\nMedicationID: ").append(rs.getInt("MedicationID")).append("\n - Name: ").append(rs.getString("Name")).append("\n - Dosage: ").append(rs.getString("Dosage")).append("\n");
            }
            return medications.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Constantes.MEDICATIONDBERROR + e.getMessage();
        }
    }

    //XML

    public int saveToXML(List<Record> records, List<PrescribedMedication> medications, List<Patient> patients, List<Doctor> doctors) {
        try {
            RecordsXML recordsXML = new RecordsXML();
            List<RecordXML> recordXMLList = new ArrayList<>();

            for (Record r : records) {
                RecordXML recordXML = new RecordXML();
                MedicationsXML medicationsXML = new MedicationsXML();
                MedicationAdapter medicationAdapter = new MedicationAdapter();
                PatientAdapter patientAdapter = new PatientAdapter();
                //medications
                medicationsXML.setMedication(medications.stream().filter(m -> m.getRecordID() == r.getRecordID()).map(medicationAdapter::marshal).toList());
                recordXML.setId(r.getRecordID());
                recordXML.setMedications(medicationsXML);

                //patient
                Optional<Patient> patient = patients.stream().filter(p -> p.getPatientID() == r.getPatientID()).findFirst();
                patient.ifPresent(value -> recordXML.setPatient(patientAdapter.marshal(value)));

                //doctor name that attended the record
                recordXML.setDoctor(doctors.stream().filter(d -> d.getDoctorID() == r.getDoctorID()).findFirst().get().getName());
                recordXML.setDiagnosis(r.getDiagnosis());
                recordXMLList.add(recordXML);
            }
            recordsXML.setRecords(recordXMLList);

            Path xmlFilePath = Paths.get(Configuration.getInstance().getRecordXmlDataFile());
            File file = xmlFilePath.toFile();
            getMarshaller().marshal(recordsXML, file);
            return 1;
        } catch (Exception e) {
            log.error("There was an error while saving the records to the xml file" + e.getMessage());
            return -1;
        }
    }

    private Marshaller getMarshaller() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RecordsXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return marshaller;
        } catch (Exception e) {
            log.error("Error creating Marshaller: " + e.getMessage());
            return null;
        }
    }

    public int saveToXML(RecordsXML recordsXML) {
        try {
            Path xmlFilePath = Paths.get(Configuration.getInstance().getRecordXmlDataFile());
            File file = xmlFilePath.toFile();
            getMarshaller().marshal(recordsXML, file);
            return 1;
        } catch (Exception e) {
            log.error("There was an error while saving the records to the xml file" + e.getMessage());
            return -1;
        }
    }

    public RecordsXML readRecordsFromXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RecordsXML.class);
            return (RecordsXML) jaxbContext.createUnmarshaller().unmarshal(new File(Configuration.getInstance().getRecordXmlDataFile()));
        } catch (Exception e) {
            log.error("Error reading records from XML file: " + e.getMessage());
            return null;
        }
    }

    public int getNewRecordID() {
        return Integer.parseInt(Configuration.getInstance().getLastRecordID()) + 1;
    }

    public int deleteByPatient(int id) {
        try {
            List<Record> records = getAll().get();
            records.removeIf(r -> r.getPatientID() == id);
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), "recordID;patientID;diagnosis;doctorID".getBytes());
            for (Record record : records) {
                save(record);
            }
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public boolean hasMedications(int id, MedicationService medicationService) {
        try(Connection con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.COUNTMEDICATIONSFROMRECORDID_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getInt("count") > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public List<Integer> getRecordIdsFromPatientId(int patientID) {
        //recordID;patientID;diagnosis;doctorID -> recordID from patientID
        return getAll().get().stream()
                .filter(r -> r.getPatientID() == patientID)
                .map(Record::getRecordID)
                .toList();
    }

    public List<PrescribedMedication> medicationsFromAPatientXML(int patientID) {
        List<PrescribedMedication> medications = new ArrayList<>();
        try {
            RecordsXML recordsXML = readRecordsFromXML();
            if (recordsXML == null) {
                return medications;
            }
            for (RecordXML recordXML : recordsXML.getRecords()) {
                if (recordXML.getPatient().getPatientID() == patientID) {
                    if (recordXML.getMedications() != null && recordXML.getMedications().getMedication() != null) {
                        recordXML.getMedications().getMedication().forEach(medicationXML ->
                                medications.add(new MedicationAdapter().unmarshal(medicationXML)));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error reading medications from XML file: " + e.getMessage());
        }
        return medications;
    }

    // Append a new medical order to a given patient in the xml
    public int appendMedicationToPatientXML(int patientID, PrescribedMedication medication) {
        try {
            RecordsXML recordsXML = readRecordsFromXML();
            if (recordsXML == null) {
                return -1;
            }
            for (RecordXML recordXML : recordsXML.getRecords()) {
                if (recordXML.getPatient().getPatientID() == patientID) {
                    if (recordXML.getMedications() == null) {
                        recordXML.setMedications(new MedicationsXML());
                    }
                    recordXML.getMedications().getMedication().add(new MedicationAdapter().marshal(medication));
                    saveToXML(recordsXML);
                    return 1;
                }
            }
        } catch (Exception e) {
            log.error("Error appending medication to XML file: " + e.getMessage());
        }
        return -1;
    }

    public int deletePatientXML(int id) {
        //deleting the patient and all his records
        try {
            RecordsXML recordsXML = readRecordsFromXML();
            if (recordsXML == null) {
                return -1;
            }
            recordsXML.getRecords().removeIf(r -> r.getPatient().getPatientID() == id);
            saveToXML(recordsXML);
            return 1;
        } catch (Exception e) {
            log.error("Error deleting patient from XML file: " + e.getMessage());
            return -1;
        }
    }

    public int appendRecordXML(int patientID, String diagnosis, String doctorName) {
        try {
            RecordsXML recordsXML = readRecordsFromXML();
            if (recordsXML == null) {
                return -1;
            }
            //ID, medications, patient, doctor, diagnosis
            RecordXML recordXML = new RecordXML(getNewRecordID(), new MedicationsXML(), getPatientFromIDXML(patientID), doctorName, diagnosis);

            recordsXML.getRecords().add(recordXML);
            saveToXML(recordsXML);
            return 1;
        } catch (Exception e) {
            log.error("Error appending record to XML file: " + e.getMessage());
            return -1;
        }
    }

    private PatientXML getPatientFromIDXML(int id) {
        try {
            RecordsXML recordsXML = readRecordsFromXML();
            if (recordsXML != null) {
                for (RecordXML recordXML : recordsXML.getRecords()) {
                    if (recordXML.getPatient().getPatientID() == id) {
                        return recordXML.getPatient();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error getting patient from XML file: " + e.getMessage());
        }
        return null;
    }

    public Either<String, List<Record>> getRecords(int patientId) {

        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.RECORDSFROMPATIENTID_QUERY)
            ) {
            preparedStatement.setInt(1, patientId);
            ResultSet rs = preparedStatement.executeQuery();
            List<Record> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Record(rs.getInt("RecordID"),
                        rs.getInt("PatientID"),
                        rs.getString("Diagnosis"),
                        rs.getInt("DoctorID")
                ));
            }
            return Either.right(records);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Either.left(Constantes.PATIENTDBERROR + e.getMessage());
        }

    }

}
