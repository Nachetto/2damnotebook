package com.hospitalcrud.dao.repository.spring;

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
@Singleton
@Component
public class DBConnection {
    private final Configuration config;
    @Getter
    private final DataSource hikariDataSource;


    public DBConnection() {
        this.config = Configuration.getInstance();
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPathDbUrl());
        hikariConfig.setUsername(config.getPathDbUser());
        hikariConfig.setPassword(config.getPathDbPassword());
        hikariConfig.setDriverClassName(config.getPathDriver());
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