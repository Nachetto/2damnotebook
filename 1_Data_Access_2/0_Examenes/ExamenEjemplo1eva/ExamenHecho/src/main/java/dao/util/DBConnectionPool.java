package dao.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.configuration.Configuration;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBConnectionPool {
    private final Configuration configuration;
    private final DataSource hikariDataSource;

    public DBConnectionPool() {
        this.configuration = Configuration.getInstance();
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(configuration.getPathDbUrl());
        hikariConfig.setUsername(configuration.getPathDbUser());
        hikariConfig.setPassword(configuration.getPathDbPassword());
        hikariConfig.setDriverClassName(configuration.getPathDriver());
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

    public DataSource getDataSource() {
        return hikariDataSource;
    }



    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}
