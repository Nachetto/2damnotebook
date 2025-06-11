package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.common.config.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Log4j2
@Component
@Singleton
public class DBConnection {
    @Getter
    private final DataSource hikariDataSource;
    private final Configuration config;
    public DBConnection() {
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

