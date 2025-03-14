package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.common.config.Configuration;
import org.example.dao.MedicationAdapter;
import org.example.dao.PatientAdapter;
import org.example.dao.RecordDao;
import org.example.domain.Doctor;
import org.example.domain.Patient;
import org.example.domain.PrescribedMedication;
import org.example.domain.Record;
import org.example.domain.xml.*;
import org.example.service.MedicationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class RecordDaoImpl implements RecordDao {

    @Override
    public Either<String, List<Record>> getAll() {
        List<Record> records = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getRecordDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                records.add(new Record(line));
            }
            return Either.right(records);
        } catch (IOException | NumberFormatException e) {
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
        try {
            //set the last recordID property in the configuration file properties.txt
            Configuration.getInstance().setLastRecordID(r.getRecordID());
            Files.write(Paths.get(Configuration.getInstance().getRecordDataFile()), ('\n' + r.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Record initialrecord, Record modifiedrecord) {
        delete(initialrecord);
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
        return medicationService.getAll().get().stream()
                .anyMatch(m -> getAll().get().stream().anyMatch
                        (r -> r.getRecordID() == m.getRecordID()
                                && r.getPatientID() == id));
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
            RecordXML recordXML = new RecordXML(getNewRecordID(),new MedicationsXML(),getPatientFromIDXML(patientID),doctorName, diagnosis);

            recordsXML.getRecords().add(recordXML);
            saveToXML(recordsXML);
            return 1;
        } catch (Exception e) {
            log.error("Error appending record to XML file: " + e.getMessage());
            return -1;
        }
    }

    private PatientXML getPatientFromIDXML(int id){
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
}
