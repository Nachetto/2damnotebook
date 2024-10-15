package com.hospitalcrud.common.config;

import com.hospitalcrud.common.Constants;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Singleton
@Getter
@Log4j2
public class Configuration {

    private String pathPatients;
    private String pathDoctors;
    private String lastIdDoctor;
    private String lastIdPatient;

    public Configuration(){
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_PATH)));
            this.pathPatients = properties.getProperty(Constants.PATH_PATIENTS);
            this.pathDoctors = properties.getProperty(Constants.PATH_DOCTORS);
            this.lastIdDoctor = properties.getProperty(Constants.LAST_ID_DOCTOR);
            this.lastIdPatient = properties.getProperty(Constants.LAST_ID_PATIENT);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }



}
