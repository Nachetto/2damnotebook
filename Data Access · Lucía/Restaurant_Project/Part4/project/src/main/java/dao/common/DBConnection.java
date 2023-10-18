package dao.common;

import common.config.Configuration;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final Configuration config;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {

        Connection conn = DriverManager
                .getConnection(config.getProperty("urlDB"), config.getProperty("user_name"), config.getProperty("password"));
        System.out.println("Connected to DB");
        return conn;
    }
}
