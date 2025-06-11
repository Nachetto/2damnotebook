package dao.impl;

import common.Constants;
import dao.OrderDAO;
import dao.common.DBConnection;
import dao.common.SQLConstants;
import jakarta.inject.Inject;
import model.Order;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Log4j2
public class OrderDAOImpl implements OrderDAO {

    final DBConnection db;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public OrderDAOImpl(DBConnection db) {
        this.db = db;

        jdbcTemplate= new JdbcTemplate(db.getDataSource());
    }

    private RowMapper<Order> orderRowMapper = (rs, rowNum) -> new Order(
            rs.getInt("order_id"),
            rs.getInt("table_id"),
            rs.getInt("customer_id"),
            rs.getTimestamp("order_date").toLocalDateTime()
    );

    @Override
    public Either<String, List<Order>> getAll() {
        try {
            String sql = SQLConstants.SELECT_ORDERS_QUERY;
            List<Order> orders = jdbcTemplate.query(sql, orderRowMapper);
            return Either.right(orders);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.ORDERDBERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, Order> get(int id) {
        try {
            String sql = SQLConstants.SELECT_ORDER_QUERY;
            Order order = jdbcTemplate.queryForObject(sql, new Object[]{id}, orderRowMapper);
            return Either.right(order);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.ERROROBTAININGORDER + e.getMessage());
        }
    }

    @Override
    public int save(Order order) {
        try {
            String sql = SQLConstants.INSERT_ORDER_QUERY;
            return jdbcTemplate.update(sql,
                    order.getOrderid(),
                    java.sql.Timestamp.valueOf(order.getOrderdate()),
                    order.getCustomerid(),
                    order.getTableid());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int modify(Order initialOrder, Order modifiedOrder) {
        if (delete(initialOrder) == 1) {
            return save(modifiedOrder);
        }
        return -1;
    }

    @Override
    public int delete(Order order) {
        try {
            String sql = SQLConstants.DELETE_ORDER_QUERY;
            return jdbcTemplate.update(sql, order.getOrderid());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
}
