package com.hospitalcrud.dao.repository.jdbc;
import com.hospitalcrud.common.config.Configuration;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
class DBConnection {

    private final Configuration config;

    public DBConnection() {
        this.config = Configuration.getInstance();
    }

    public Connection getConnection() throws SQLException {

        Connection conn = DriverManager
                .getConnection(config.getPathDbUrl(), config.getPathDbUser(), config.getPathDbPassword());
        System.out.println("Connected to DB");
        return conn;
    }

    public void closeConnection(Connection connArg) {
        System.out.println("Releasing all open resources ...");
        try {
            if (connArg != null) {
                connArg.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}