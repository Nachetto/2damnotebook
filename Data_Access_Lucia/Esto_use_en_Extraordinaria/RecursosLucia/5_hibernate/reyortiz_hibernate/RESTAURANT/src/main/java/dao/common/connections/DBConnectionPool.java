package dao.common.connections;

import common.Configuration;
import dao.common.SqlQueries;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Singleton
public class DBConnectionPool {
    private final Configuration configuration;
    private final DataSource hikariDataSource;

    @Inject
    private DBConnectionPool(Configuration configuration){
        this.configuration = configuration;
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(configuration.getPropertyXML(SqlQueries.URL));
        hikariConfig.setUsername(configuration.getPropertyXML(SqlQueries.USER));
        hikariConfig.setPassword(configuration.getPropertyXML(SqlQueries.SQL_PASSWORD));
        hikariConfig.setDriverClassName(configuration.getPropertyXML(SqlQueries.DRIVER));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty(SqlQueries.HIKARICACHEPREP, true);
        hikariConfig.addDataSourceProperty(SqlQueries.HIKARICACHESIZE, 250);
        hikariConfig.addDataSourceProperty(SqlQueries.HIKARICACHELIMIT, 2048);

        return new HikariDataSource(hikariConfig);

    }

    public DataSource getDataSource(){
        return hikariDataSource;
    }

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void closePool(){
        ((HikariDataSource) hikariDataSource).close();
    }
}
