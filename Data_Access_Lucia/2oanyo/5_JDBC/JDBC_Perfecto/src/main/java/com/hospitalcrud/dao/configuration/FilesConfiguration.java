package com.hospitalcrud.dao.configuration;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
@Getter
public class FilesConfiguration {

    private static FilesConfiguration instance = null;
    private Path pathPatients;
    private Path pathDoctors;
    private int lastID;

    private FilesConfiguration() {
        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader()
                    .getResourceAsStream("config/properties"));
            this.pathPatients = Paths.get(p.getProperty("pathPatients"));
            this.pathDoctors = Paths.get(p.getProperty("pathDoctors"));
            this.lastID = Integer.parseInt(p.getProperty("nextID"));
        } catch (IOException e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static FilesConfiguration getInstance() {
        if (instance==null) {
            instance=new FilesConfiguration();
        }
        return instance;
    }
    public void setID (int lastID) {
        this.lastID = lastID;
    }
}
