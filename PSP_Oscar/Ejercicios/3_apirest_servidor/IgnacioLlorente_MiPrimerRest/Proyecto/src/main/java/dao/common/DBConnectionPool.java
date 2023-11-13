package dao.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Constants;
import common.config.Configuration;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
@Log4j2
public class DBConnectionPool {

    private final Configuration config;
    private final DataSource hikariDataSource;


    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty(SQLConstants.URL));
        hikariConfig.setUsername(config.getProperty(SQLConstants.USERNAME));
        hikariConfig.setPassword(config.getProperty(SQLConstants.PASSWORD));
        hikariConfig.setDriverClassName(config.getProperty(SQLConstants.DRIVER_CLASS_NAME));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty(SQLConstants.CACHE, true);
        hikariConfig.addDataSourceProperty(SQLConstants.CACHESIZE, 250);
        hikariConfig.addDataSourceProperty(SQLConstants.CACHELIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }
    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            log.error(Constants.ERRDBCLOSE+e.getMessage(), e);

        }
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error(Constants.ERRDB+e.getMessage(), e);
        }

        return con;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}

