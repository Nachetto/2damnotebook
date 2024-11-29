package dao.utilities;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.configuration.Configuration;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.Getter;

import javax.sql.DataSource;

@Singleton
public class DbConnection {
    @Getter
    private final DataSource hikariDataSource;
    private final Configuration config;
    public DbConnection() {
        this.config = Configuration.getInstance();
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPathDbUrl());//URL BBDD
        hikariConfig.setUsername(config.getPathDbUser());//USER BBDD
        hikariConfig.setPassword(config.getPathDbPassword());//PASS BBDD
        hikariConfig.setDriverClassName(config.getPathDriver());//com.mysql.cj.jdbc.Driver
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        return new HikariDataSource(hikariConfig);
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}

