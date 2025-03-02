package com.hospitalcrud.dao.utilities;

import com.hospitalcrud.dao.configuration.XMLConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Component
public class DBConnectionPool {
    private XMLConfiguration configuration;
    private DataSource hikariDataSource;

    public DBConnectionPool() {
        this.configuration = XMLConfiguration.getInstance();
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(configuration.getDbUrl());
        hikariConfig.setUsername(configuration.getUser_name());
        hikariConfig.setPassword(configuration.getPassword());
        hikariConfig.setDriverClassName(configuration.getDriver());
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}
