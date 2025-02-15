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

    private String pathDbUrl  ;
    private String pathDbUser ;
    private String pathDbPassword ;
    private String pathDriver;

    public Configuration() {
        try {
            properties = new Properties();
            properties.loadFromXML(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_PATH_XML)));


            this.pathDbUrl = properties.getProperty(Constants.PATH_DB_URL);
            this.pathDbUser = properties.getProperty(Constants.PATH_DB_USER);
            this.pathDbPassword = properties.getProperty(Constants.PATH_DB_PASSWORD);
            this.pathDriver = properties.getProperty(Constants.PATH_DRIVER);
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