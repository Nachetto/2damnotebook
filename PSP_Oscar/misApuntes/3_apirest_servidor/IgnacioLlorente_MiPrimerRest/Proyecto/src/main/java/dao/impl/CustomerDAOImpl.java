package dao.impl;

import common.Constants;
import dao.CustomerDAO;
import dao.common.DBConnectionPool;
import dao.common.ConstantesDao;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerDAOImpl implements CustomerDAO {

    private final DBConnectionPool db;

    @Inject
    public CustomerDAOImpl(DBConnectionPool db) {
        this.db = db;
    }

    public Either<String, List<Customer>> getAll() throws BaseDatosCaidaException, NotFoundException {
        try (Connection myConnection = db.getConnection();
             Statement stmt = myConnection.createStatement();
             PreparedStatement preparedStatement = myConnection.prepareStatement(ConstantesDao.SELECT_CREDS_FROM_CUSTOMER_QUERY)) {

            ResultSet rs = stmt.executeQuery(ConstantesDao.SELECT_CUSTOMERS_QUERY);
            List<Customer> customers = new ArrayList<>();

            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt(Constants.ID));
                ResultSet rsCreds = preparedStatement.executeQuery();
                if (!rsCreds.next()) {
                    log.error(Constants.CREDENTALSNOTFOUND + rs.getInt(Constants.ID));
                    throw new NotFoundException(Constants.CREDENTALSNOTFOUND + rs.getInt(Constants.ID));
                }
                Customer resultCustomer = new Customer(
                        rs.getInt(Constants.ID),
                        rs.getInt(Constants.PHONE),
                        rs.getString(Constants.NAME),
                        rs.getString(Constants.LASTNAME),
                        rs.getString(Constants.EMAIL),
                        new Credential(
                                rsCreds.getString(Constants.USERNAME),
                                rsCreds.getString(Constants.PASSWORD)),
                        rs.getDate(Constants.BIRTHDATE).toLocalDate()
                );
                customers.add(resultCustomer);
            }
            return Either.right(customers);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_TODOS_LOS_CLIENTES + ex.getMessage());
        }
    }

    public Either<String, Customer> get(int id) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (id <= 0) {
            throw new OtraException(ConstantesDao.INVALIDID + id);
        }
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.SELECT_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.CLIENTE_NO_ENCONTRADO_CON_ID + id);
            }
            return Either.right(new Customer(rs.getInt(Constants.ID), rs.getInt(Constants.PHONE), rs.getString(Constants.NAME), rs.getString(Constants.LASTNAME), rs.getString(Constants.EMAIL), new Credential("", ""), rs.getDate(Constants.BIRTHDATE).toLocalDate()));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_OBTENER_EL_CLIENTE_CON_ID + id + ", " + ex.getMessage());
        }
    }

    @Override
    public int save(Customer c) throws BaseDatosCaidaException, OtraException {
        if (c.getId() <= 0) {
            throw new OtraException(ConstantesDao.INVALIDID + c.getId());
        }
        try
                (Connection con = db.getConnection();
                 PreparedStatement checkExistence = con.prepareStatement(ConstantesDao.CHECK_CUSTOMER_EXISTENCE_QUERY);
                 PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.ADD_CUSTOMER_QUERY);
                 PreparedStatement credentials = con.prepareStatement(ConstantesDao.ADD_CREDENTIALS_QUERY))
        {
            checkExistence.setInt(1, c.getId());
            ResultSet rsExistence = checkExistence.executeQuery();
            if (rsExistence.next()) {
                throw new OtraException(ConstantesDao.EXISTE_CON_ID + c.getId());
            }

            preparedStatement.setInt(1, c.getId());
            preparedStatement.setString(2, c.getName());
            preparedStatement.setString(3, c.getSurname());
            preparedStatement.setString(4, c.getEmail());
            preparedStatement.setInt(5, c.getPhone());
            preparedStatement.setDate(6, Date.valueOf(c.getBirthdate()));
            credentials.setInt(1, c.getId());
            credentials.setString(2, c.getCredential().username());
            credentials.setString(3, c.getCredential().password());
            if (preparedStatement.executeUpdate() != -1) {
                return credentials.executeUpdate();
            }
            return -1;

        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new BaseDatosCaidaException(ConstantesDao.ERROR_AL_GUARDAR_EL_CLIENTE + ex.getMessage());
        }
    }

    @Override
    public int modify(Customer initialcustomer, Customer modifiedcustomer) throws BaseDatosCaidaException, OtraException {
        if (initialcustomer == null || modifiedcustomer == null) {
            throw new OtraException(ConstantesDao.ES_NULO);
        }
        int deletionResult = delete(initialcustomer);
        if (deletionResult == 1) {
            return save(modifiedcustomer);
        } else {
            throw new OtraException(ConstantesDao.ERROR_AL_MODIFICAR_EL_CLIENTE_CON_ID + initialcustomer.getId());
        }
    }

    public boolean hasOrders(Customer c) throws BaseDatosCaidaException, NotFoundException, OtraException {
        if (c == null || c.getId() <= 0) {
            throw new OtraException(ConstantesDao.ID_DEL_CLIENTE_NO_VALIDO_O_NULO);
        }
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(ConstantesDao.CHECK_ORDERS_BY_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, c.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.CLIENTE_NO_ENCONTRADO_CON_ID + c.getId());
            }
            return rs.getInt(Constants.COUNT) > 0;
        } catch (SQLException ex) {
            throw new BaseDatosCaidaException(ConstantesDao.VERIFICAR_ORDENES_DEL_CLIENTE + ex.getMessage());
        }
    }
    @Override
    public int delete(Customer c) throws OtraException, NotFoundException {
        if (c == null) {
            throw new OtraException(ConstantesDao.ELIMINAR_ES_NULO);
        }
        try (Connection con = db.getConnection();
             PreparedStatement checkExistence = con.prepareStatement(ConstantesDao.CHECK_CUSTOMER_EXISTENCE_QUERY)) {
            checkExistence.setInt(1, c.getId());
            ResultSet rs = checkExistence.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException(ConstantesDao.CLIENTE_NO_ENCONTRADO_CON_ID + c.getId());
            }
            borrarOrderyOrderItems(c, con);
            try (PreparedStatement deleteCredentials = con.prepareStatement(ConstantesDao.DELETE_CREDENTIALS_QUERY);
                 PreparedStatement deleteCustomer = con.prepareStatement(ConstantesDao.DELETE_CUSTOMER_QUERY)) {

                deleteCredentials.setInt(1, c.getId());
                deleteCustomer.setInt(1, c.getId());
                deleteCredentials.executeUpdate();
                deleteCustomer.executeUpdate();
                return 1;

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new OtraException(ConstantesDao.ERROR_AL_ELIMINAR_EL_CLIENTE + ex.getMessage());
        }
    }
    private void borrarOrderyOrderItems(Customer c, Connection con) throws SQLException {
        try (PreparedStatement deleteOrderItems = con.prepareStatement(ConstantesDao.DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY);
             PreparedStatement deleteOrders = con.prepareStatement(ConstantesDao.DELETE_ORDERS_BY_CUSTOMER_QUERY)) {
            con.setAutoCommit(false);
            deleteOrderItems.setInt(1, c.getId());
            deleteOrders.setInt(1, c.getId());

            deleteOrderItems.executeUpdate();
            deleteOrders.executeUpdate();

            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        }
    }


}
