package dao.repository;

import common.Constants;
import common.configuration.Configuration;
import dao.model.Visitor;
import dao.utilities.DbConnection;
import domain.error.InternalServerErrorException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VisitorDao {
    private final DbConnection pool;

    @Inject
    public VisitorDao(DbConnection pool) {
        this.pool=pool;
    }

    public int add(Visitor v) throws InternalServerErrorException{
        try(Connection con = pool.getHikariDataSource().getConnection();
            PreparedStatement addStatement = con.prepareStatement(Constants.ADD_VISITOR_QUERY)
        ){//INSERT INTO Visitors(Name,Email,Tickets)
            addStatement.setString(1,v.getName());
            addStatement.setString(2,v.getEmail());
            addStatement.setInt(3,v.getTickets());
            return addStatement.executeUpdate();

        }catch (SQLException e){
            throw new InternalServerErrorException("Database connection error: "+e.getMessage());
        }
    }
}
