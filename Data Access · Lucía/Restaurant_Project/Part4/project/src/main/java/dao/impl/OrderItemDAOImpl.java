package dao.impl;

import common.Constants;
import dao.OrderItemDAO;
import dao.common.DBConnection;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.common.SQLConstants;

@Log4j2
public class OrderItemDAOImpl implements OrderItemDAO {

    private final DBConnection db;

    @Inject
    public OrderItemDAOImpl(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<String, List<OrderItem>> getAll() {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_ORDER_ITEMS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<OrderItem> orderItems = new ArrayList<>();
            while (rs.next()) {
                OrderItem resultOrderItem = new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("menu_item_id"),
                        rs.getInt("quantity")
                );
                orderItems.add(resultOrderItem);
            }
            return Either.right(orderItems);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.ORDER_ITEM_DB_ERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, OrderItem> get(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_ORDER_ITEM_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                OrderItem resultOrderItem = new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("menu_item_id"),
                        rs.getInt("quantity")
                );
                return Either.right(resultOrderItem);
            }
            return Either.left(Constants.ID_NOT_FOUND + id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.ERROR_OBTAINING_ORDER_ITEM + e.getMessage());
        }
    }

    @Override
    public int save(OrderItem o) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.INSERT_ORDER_ITEM_QUERY)) {
            preparedStatement.setInt(1, o.getOrder_item_id());
            preparedStatement.setInt(2, o.getOrder_id());
            preparedStatement.setInt(3, o.getOrder_item_id());
            preparedStatement.setInt(4, o.getQuantity());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(OrderItem o) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.UPDATE_ORDER_ITEM_QUERY)) {
            preparedStatement.setInt(1, o.getOrder_item_id());
            preparedStatement.setInt(2, o.getOrder_id());
            preparedStatement.setInt(3, o.getOrder_item_id());
            preparedStatement.setInt(4, o.getQuantity());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int delete(OrderItem o) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.DELETE_ORDER_ITEM_QUERY)) {
            preparedStatement.setInt(1, o.getOrder_item_id());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
