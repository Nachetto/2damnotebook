package common;

import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Log4j2
@Singleton
public class Configuration {
    private static Configuration instance = null;
    private Properties propertiestxt;
    private Properties propertiesxml;

    private Configuration() {
        try {
            //text files
            propertiestxt = new Properties();
            propertiestxt.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(UtilitiesCommon.PROPERTIESTXT)));
            //xml files
            propertiesxml = new Properties();
            propertiesxml.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream(UtilitiesCommon.PROPERTIESXML));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getPropertyTXT(String key) {
        return propertiestxt.getProperty(key);
    }

    public String getPropertyXML(String key) {
        return propertiesxml.getProperty(key);
    }

}
