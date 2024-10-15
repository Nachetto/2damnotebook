package org.example.config;


import org.example.common.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    private static Configuration instance = null;
    private Properties p;

    private Configuration() {
        Path p1 = Paths.get(Constants.PROPERTIES_XML);
        p = new Properties();
        InputStream propertiesStream;
        try {
            propertiesStream = Files.newInputStream(p1);
            p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
