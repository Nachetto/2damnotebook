package dao.common.connections;

import common.Configuration;
import dao.common.SqlQueries;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final Configuration configuration;

    @Inject
    public DBConnection(Configuration configuration){
        this.configuration = configuration;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(configuration.getPropertyXML(SqlQueries.URL), configuration.getPropertyXML(SqlQueries.USER), configuration.getPropertyXML(SqlQueries.SQL_PASSWORD));
    }


}
