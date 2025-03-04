package common.configuration;
import common.Constants;
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
    private static Configuration instance=null;
    private Properties properties;

    private String pathVisitsXML;

    private String pathDbUrl  ;
    private String pathDbUser ;
    private String pathDbPassword ;
    private String pathDriver;

    public Configuration() {
        try {
            properties = new Properties();
            properties.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.FILE_PATH )));

            this.pathVisitsXML= properties.getProperty(Constants.PATH_FACTIONS);

            this.pathDbUrl = properties.getProperty(Constants.PATH_DB_URL);
            this.pathDbUser = properties.getProperty(Constants.PATH_DB_USER);
            this.pathDbPassword = properties.getProperty(Constants.PATH_DB_PASSWORD);
            this.pathDriver = properties.getProperty(Constants.PATH_DRIVER);

        } catch (IOException ex) {
            log.info(ex.getMessage()+ ex);
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
}