package org.example.common.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Getter
@Log4j2
public class Configuration {
    private final Properties properties;
    private final Properties passwords;
    private static Configuration instance;

    private Configuration() {
        properties = new Properties();
        passwords = new Properties();
        try {
            properties.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
            passwords.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/passwords.xml"));
        } catch (IOException e) {
            System.out.println(Constantes.ERRDB);
        }
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getDoctorDataFile() {
        return getProperty("doctorDataFile");
    }

    public String getPatientDataFile() {
        return getProperty("patientDataFile");
    }

    public String getMedicationDataFile() {
        return getProperty("medicationDataFile");
    }

    public String getRecordDataFile() {
        return getProperty("recordDataFile");
    }

    public String getPassword(String key) {
        return passwords.getProperty(key);
    }

    public String getLastRecordID() {
        return properties.getProperty("lastRecordID");
    }

    public void setLastRecordID(int id) {
        properties.setProperty("lastRecordID", String.valueOf(id));
    }

    public String getRecordXmlDataFile() {
        return getProperty("recordXmlDataFile");
    }

    public String getLastMedicationID() {
        return properties.getProperty("lastMedicationID");
    }

    public void setLastMedicationID(int medicationID) {
        properties.setProperty("lastMedicationID", String.valueOf(medicationID));
    }
}
