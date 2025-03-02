package dao.impl;

import common.RestaurantError;
import dao.CredentialsDAO;
import dao.common.connections.DBConnectionPool;
import dao.common.SqlQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.Credentials;


import java.sql.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class CredentialsDAOImpl implements CredentialsDAO {

    private final DBConnectionPool dbConnection;

    @Inject
    public CredentialsDAOImpl(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Either<RestaurantError, List<Credentials>> getAll() {
        Either<RestaurantError, List<Credentials>> either;

        try (Connection myConnection = dbConnection.getConnection();
             Statement statement = myConnection.createStatement()) {
            ResultSet rs = statement.executeQuery(SqlQueries.CREDENTIALSGETALL);

            Either<RestaurantError, List<Credentials>> read = readRS(rs);
            if (read.isRight()) {
                either = Either.right(read.get());
            } else {
                either = Either.left(read.getLeft());
            }
        } catch (SQLException e) {
            either = Either.left(new RestaurantError(0, e.getMessage()));
        }
        return either;
    }

    @Override
    public Either<RestaurantError, Credentials> get(int id) {
        Either<RestaurantError, Credentials> either;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.CREDENTIALSGET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Either<RestaurantError, List<Credentials>> read = readRS(resultSet);
            if (read.isRight()) {
                either = Either.right(read.get().get(0));
            } else {
                either = Either.left(read.getLeft());
            }
        } catch (SQLException e) {
            either = Either.left(new RestaurantError(0, e.getMessage()));
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(Credentials credentials) {
        return null;
    }

    @Override
    public Either<RestaurantError, Integer> update(Credentials credentials) {
        return null;
    }

    @Override
    public Either<RestaurantError, Integer> delete(Credentials credentials) {
        return null;
    }

    private Either<RestaurantError, List<Credentials>> readRS(ResultSet rs){
        Either<RestaurantError, List<Credentials>> either;
        List<Credentials> list = new ArrayList<>();

        try {
            while (rs.next()) {
                int id = rs.getInt(SqlQueries.IDCREDENTIALS);
                String username = rs.getString(SqlQueries.USERNAME);
                String password = rs.getString(SqlQueries.PASSWORD);

                list.add(new Credentials(id, username, password));
            }
            either = Either.right(list);
        } catch (SQLException e) {
            log.error(e.getMessage());
            either = Either.left(new RestaurantError(0, e.getMessage()));
        }

        return either;
    }
}

