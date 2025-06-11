package com.hospitalcrud.common.config;

import com.hospitalcrud.common.Constants;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Singleton
@Getter
@Log4j2
public class Configuration {
    private static Configuration instance=null;
    private Properties properties;

    private String pathPatients;
    private String pathDoctors;
    private String pathMedicalRecords;
    private String nextIdDoctor;
    private String nextIdPatient;
    private String nextIdMedRecord;

    public Configuration() {
        try {
            properties = new Properties();
            properties.loadFromXML(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_PATH_XML)));
            this.pathPatients = properties.getProperty(Constants.PATH_PATIENTS);
            this.pathDoctors = properties.getProperty(Constants.PATH_DOCTORS);
            this.pathMedicalRecords = properties.getProperty(Constants.PATH_MEDICAL_RECORDS);
            this.nextIdDoctor = properties.getProperty(Constants.NEXT_ID_DOCTOR);
            this.nextIdPatient = properties.getProperty(Constants.NEXT_ID_PATIENT);
            this.nextIdMedRecord = properties.getProperty(Constants.NEXT_ID_MED_RECORD);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public void setNextIdDoctor(String nextIdDoctor) {
        this.nextIdDoctor = nextIdDoctor;
        updateProperties(Constants.NEXT_ID_DOCTOR, nextIdDoctor);
    }

    public void setNextIdPatient(String nextIdPatient) {
        this.nextIdPatient = nextIdPatient;
        updateProperties(Constants.NEXT_ID_PATIENT, nextIdPatient);
    }

    public void setNextIdMedRecord(String nextIdMedRecord) {
        this.nextIdMedRecord = nextIdMedRecord;
        updateProperties(Constants.NEXT_ID_MED_RECORD, nextIdMedRecord);
    }

    private void updateProperties(String key, String value) {
        try (FileOutputStream output = new FileOutputStream(
                Objects.requireNonNull(Configuration.class.getClassLoader().getResource(Constants.CONFIG_FILE_PATH_XML)).getFile())) {
            properties.setProperty(key, value);
            properties.storeToXML(output, null);
        } catch (IOException ex) {
            log.error("Error updating properties file", ex);
        }
    }
}