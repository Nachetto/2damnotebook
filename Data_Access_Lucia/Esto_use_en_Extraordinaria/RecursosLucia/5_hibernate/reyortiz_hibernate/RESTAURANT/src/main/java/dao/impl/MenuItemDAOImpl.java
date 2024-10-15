package dao.impl;

import common.RestaurantError;
import dao.common.connections.DBConnectionPool;
import dao.common.SqlQueries;
import dao.MenuItemDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.Data;
import model.MenuItem;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuItemDAOImpl implements MenuItemDAO {
    private DBConnectionPool dbConnection;

    @Inject
    public MenuItemDAOImpl(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Either<RestaurantError, List<MenuItem>> getAll() {
        Either<RestaurantError, List<MenuItem>> either;

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(SqlQueries.MENUITEMGETALL);
            Either<RestaurantError, List<MenuItem>> read = readRS(resultSet);
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
    public Either<RestaurantError, MenuItem> get(int id) {
        Either<RestaurantError, MenuItem> either;
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.MENUITEMGET)){

            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Either<RestaurantError, List<MenuItem>> read = readRS(resultSet);
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
    public Either<RestaurantError, Integer> add(MenuItem menuItem) {
        return null;
    }
    @Override
    public Either<RestaurantError, Integer> update(MenuItem menuItem) {
        return null;
    }
    @Override
    public Either<RestaurantError, Integer> delete(MenuItem menuItem) {
        return null;
    }

    private Either<RestaurantError, List<MenuItem>> readRS(ResultSet resultSet) {
        Either<RestaurantError, List<MenuItem>> either;
        List<MenuItem> list  = new ArrayList<>();
        try {
            while (resultSet.next()){
                int id = resultSet.getInt(SqlQueries.IDMENU_ITEM);
                String name = resultSet.getString(SqlQueries.NAME);
                String description = resultSet.getString(SqlQueries.DESCRIPTION);
                double price = resultSet.getFloat(SqlQueries.PRICE);
                list.add(new MenuItem(id,name,description,price));
            }
            either = Either.right(list);
        } catch (SQLException e) {
            either = Either.left(new RestaurantError(0,e.getMessage()));
        }
        return either;
    }

}
