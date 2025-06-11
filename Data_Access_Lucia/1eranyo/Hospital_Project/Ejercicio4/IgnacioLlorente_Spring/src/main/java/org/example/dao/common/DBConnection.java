package org.example.dao.common;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.config.Configuration;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DBConnection {
    private final Configuration config;
    private Connection connection;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    @PreDestroy
    public void closePool() {
        // Cerrar el pool de conexiones antes ce que se destruya el objeto
        try {
            close();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(config.getProperty("urlDB"), config.getProperty("user_name"), config.getProperty("password"));
        }
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}