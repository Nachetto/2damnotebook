package common.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuracion {

    private String pathDatos;
    private String baseUrl;

    public Configuracion() {
        try {
            Properties p = new Properties();
            Properties p2 = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream("config/config.properties"));
            this.pathDatos = p.getProperty("pathDatos");
            p2.load(getClass().getClassLoader().getResourceAsStream("config/config.properties"));
            this.baseUrl = p2.getProperty("baseUrl");

        } catch (IOException e) {
           log.error(e.getMessage(),e);
        }
    }
}
