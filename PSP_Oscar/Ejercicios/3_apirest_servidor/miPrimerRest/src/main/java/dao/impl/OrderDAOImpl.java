package dao.impl;

import common.Constants;
import dao.OrderDAO;
import dao.common.DBConnectionPool;
import dao.common.SQLConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OrderDAOImpl implements OrderDAO {

    private final DBConnectionPool db;
    @Inject
    public OrderDAOImpl(DBConnectionPool db) {
        this.db = db;
    }

    @Override
    public Either<String, List<Order>> getAll() {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_ORDERS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                //(int orderid, int tableid, int customerid, LocalDateTime orderdate)
                Order resultOrder = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("table_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                );
                orders.add(resultOrder);
            }
            return Either.right(orders);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.ORDERDBERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, Order> get(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_ORDER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Order resultOrder = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("table_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                );
                System.out.println(resultOrder);
                return Either.right(resultOrder);
            }
            return Either.left(Constants.IDNOTFOUND + id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.ERROROBTAININGORDER + e.getMessage());
        }
    }

    @Override
    public int save(Order o) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.INSERT_ORDER_QUERY)) {
            //(order_id, order_date, customer_id, table_id)
            preparedStatement.setInt(1, o.getOrderid());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(o.getOrderdate()));
            preparedStatement.setInt(3, o.getCustomerid());
            preparedStatement.setInt(4, o.getTableid());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(Order o, Order o2) {
        if (delete(o)==1)
            return save(o2);
        return -1;
    }

    @Override
    public int delete(Order o) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.DELETE_ORDER_QUERY)) {
            preparedStatement.setInt(1, o.getOrderid());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
