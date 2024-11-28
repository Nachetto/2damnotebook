package dao.impl;

import common.Constants;
import dao.OrderItemDAO;
import dao.common.DBConnection;
import dao.common.SQLConstants;
import jakarta.inject.Inject;
import model.OrderItem;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Log4j2
public class OrderItemDAOImpl implements OrderItemDAO {

    final DBConnection db;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public OrderItemDAOImpl(DBConnection db) {
        this.db = db;
        jdbcTemplate= new JdbcTemplate(db.getDataSource());
    }

    private RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> new OrderItem(
            rs.getInt("order_item_id"),
            rs.getInt("order_id"),
            rs.getInt("menu_item_id"),
            rs.getInt("quantity")
    );

    @Override
    public Either<String, List<OrderItem>> getAll() {
        try {
            String sql = SQLConstants.SELECT_ORDER_ITEMS_QUERY;
            List<OrderItem> orderItems = jdbcTemplate.query(sql, orderItemRowMapper);
            return Either.right(orderItems);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.ORDER_ITEM_DB_ERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, OrderItem> get(int id) {
        try {
            String sql = SQLConstants.SELECT_ORDER_ITEM_QUERY;
            OrderItem orderItem = jdbcTemplate.queryForObject(sql, new Object[]{id}, orderItemRowMapper);
            return Either.right(orderItem);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.ERROR_OBTAINING_ORDER_ITEM + e.getMessage());
        }
    }

    @Override
    public int save(OrderItem orderItem) {
        try {
            String sql = SQLConstants.INSERT_ORDER_ITEM_QUERY;
            return jdbcTemplate.update(sql,
                    orderItem.getOrder_item_id(),
                    orderItem.getOrder_id(),
                    orderItem.getMenu_item_id(),
                    orderItem.getQuantity());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int modify(OrderItem orderItem) {
        try {
            String sql = SQLConstants.UPDATE_ORDER_ITEM_QUERY;
            return jdbcTemplate.update(sql,
                    orderItem.getMenu_item_id(),
                    orderItem.getQuantity(),
                    orderItem.getOrder_item_id());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int delete(OrderItem orderItem) {
        try {
            String sql = SQLConstants.DELETE_ORDER_ITEM_QUERY;
            return jdbcTemplate.update(sql, orderItem.getOrder_item_id());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
}
