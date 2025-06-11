package com.hospitalcrud.dao.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
@Getter
public class XMLConfiguration {
    private static XMLConfiguration instance = null;
    private Path pathMedicalRecords;
    private String dbUrl;
    private String user_name;
    private String password;
    private String driver;
    @Setter
    private int lastID;

    private XMLConfiguration() {
        try {
            Properties p = new Properties();
            p.loadFromXML(getClass().getClassLoader()
                    .getResourceAsStream("config/properties.xml"));
            this.pathMedicalRecords = Paths.get(p.getProperty("pathMedicalRecords"));
            this.lastID = Integer.parseInt(p.getProperty("nextID"));
            this.dbUrl = p.getProperty("dbUrl");
            this.user_name = p.getProperty("user_name");
            this.password = p.getProperty("password");
            this.driver = p.getProperty("driver");
        } catch (IOException e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static XMLConfiguration getInstance() {
        if (instance==null) {
            instance=new XMLConfiguration();
        }
        return instance;
    }
}
