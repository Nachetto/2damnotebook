package com.hospitalcrud.common.config;

import com.google.gson.Gson;

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
    private Gson gson;

    private String pathPatients;
    private String pathDoctors;
    private String nextIdDoctor;
    private String nextIdPatient;
    private Properties properties;

    public Configuration(){
        try {
            properties = new Properties();
            properties.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_PATH)));
            this.pathPatients = properties.getProperty(Constants.PATH_PATIENTS);
            this.pathDoctors = properties.getProperty(Constants.PATH_DOCTORS);
            this.nextIdDoctor = properties.getProperty(Constants.NEXT_ID_DOCTOR);
            this.nextIdPatient = properties.getProperty(Constants.NEXT_ID_PATIENT);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static Configuration getInstance() {

        if (instance==null) {
            instance=new Configuration();
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

    private void updateProperties(String key, String value) {
        try (FileOutputStream output = new FileOutputStream(Objects.requireNonNull(Configuration.class.getClassLoader().getResource(Constants.CONFIG_FILE_PATH)).getFile())) {
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (IOException ex) {
            log.error("Error updating properties file", ex);
        }
    }
}