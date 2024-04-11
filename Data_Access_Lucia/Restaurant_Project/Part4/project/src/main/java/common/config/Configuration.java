package common.config;

import common.Constants;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Getter
@Log4j2
public class Configuration {
    private final Properties properties;
    private static Configuration instance;

    private Configuration() {
        properties = new Properties();
        try {
            properties.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
        } catch (IOException e) {
            System.out.println(Constants.ERRDB);
        }
    }

    public String getCustomerDataFile() {
        return getProperty("customerDataFile");
    }

    public String getOrderDataFile() {
        return properties.getProperty("orderDataFile");
    }

    public String getOrderItemsDataFile(){return properties.getProperty("orderItemsDataFile");}

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }



    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
