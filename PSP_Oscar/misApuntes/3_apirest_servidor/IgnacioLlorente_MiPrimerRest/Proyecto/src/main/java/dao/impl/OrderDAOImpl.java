package dao.impl;

import common.Constants;
import dao.OrderDAO;
import dao.common.DBConnectionPool;
import dao.common.ConstantesDao;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Order;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

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
    public Either<String, List<Order>> getAll() throws BaseDatosCaidaException {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_ORDERS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order resultOrder = new Order(
                        rs.getInt(Constants.ORDERID),
                        rs.getInt(Constants.TABLEID),
                        rs.getInt(Constants.CUSID),
                        rs.getTimestamp(Constants.ORDERDATE).toLocalDateTime()
                );
                orders.add(resultOrder);
            }
            return Either.right(orders);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.OBTENER_TODAS_LAS_ORDENES + e.getMessage());
        }
    }

    @Override
    public Either<String, Order> get(int id) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (id <= 0) {
            throw new OtraException(ConstantesDao.INVALIDID + id);
        }
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_ORDER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.NO_ENCONTRADA_CON_ID + id);
            }
            Order resultOrder = new Order(
                    rs.getInt(Constants.ORDERID),
                    rs.getInt(Constants.TABLEID),
                    rs.getInt(Constants.CUSID),
                    rs.getTimestamp(Constants.ORDERDATE).toLocalDateTime()
            );
            return Either.right(resultOrder);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.OBTENER_LA_ORDEN_CON_ID + id + ", " + e.getMessage());
        }
    }

    @Override
    public int save(Order o) throws BaseDatosCaidaException, OtraException {
        if (o == null || o.getOrderid() <= 0) {
            throw new OtraException(ConstantesDao.ID_DE_ORDEN_NO_VALIDO);
        }
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.INSERT_ORDER_QUERY)) {
            preparedStatement.setInt(1, o.getOrderid());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(o.getOrderdate()));
            preparedStatement.setInt(3, o.getCustomerid());
            preparedStatement.setInt(4, o.getTableid());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_LA_ORDEN + e.getMessage());
        }
    }

    @Override
    public int modify(Order o, Order o2) throws BaseDatosCaidaException, OtraException {
        if (o == null || o2 == null) {
            throw new OtraException(ConstantesDao.ORDEN_A_MODIFICAR_ES_NULA);
        }
        int deletionResult = delete(o);
        if (deletionResult == 1) {
            return save(o2);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_LA_ORDEN_CON_ID + o.getOrderid());
        }
    }

    @Override
    public int delete(Order o) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (o == null) {
            throw new OtraException(ConstantesDao.ORDEN_A_ELIMINAR_ES_NULA);
        }
        try (Connection con = db.getConnection();
             PreparedStatement checkExistence = con.prepareStatement(ConstantesDao.CHECK_ORDER_EXISTENCE_QUERY)) {
            checkExistence.setInt(1, o.getOrderid());
            ResultSet rs = checkExistence.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.NO_ENCONTRADA_CON_ID + o.getOrderid());
            }
            PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.DELETE_ORDER_QUERY);
            preparedStatement.setInt(1, o.getOrderid());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_ELIMINAR_LA_ORDEN + e.getMessage());
        }
    }
}
