package org.example.nachoHibernateConSpring.common.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.nachoHibernateConSpring.common.Constants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Singleton
@Getter
@Log4j2
@Component
public class Configuration {

    private String pathSession;
    private Properties properties;

    public Configuration(){
        try {
            properties = new Properties();
            properties.load(Objects.requireNonNull(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_PATH)));
            this.pathSession = properties.getProperty(Constants.PATH_SESSION);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }


}