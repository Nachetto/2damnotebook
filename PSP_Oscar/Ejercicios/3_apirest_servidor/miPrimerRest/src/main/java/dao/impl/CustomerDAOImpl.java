package dao.impl;

import common.Constants;
import dao.CustomerDAO;
import dao.common.DBConnectionPool;
import dao.common.SQLConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerDAOImpl implements CustomerDAO {

    private final DBConnectionPool db;
    @Getter
    @Setter
    private boolean userConfirmedDeletion = true; //esto lo dejo porque es la implementacion de lo de lucia, si no tendria que borrar demasiadas cosas y no me da tiempo

    @Inject
    public CustomerDAOImpl(DBConnectionPool db) {
        this.db = db;
    }

    public Either<String, List<Customer>> getAll() {
        try (Connection myConnection = db.getConnection(); Statement stmt = myConnection.createStatement(); PreparedStatement preparedStatement = myConnection.prepareStatement(SQLConstants.SELECT_CREDS_FROM_CUSTOMER_QUERY)) {

            ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_CUSTOMERS_QUERY);
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt(Constants.ID));
                ResultSet rsCreds = preparedStatement.executeQuery();
                if (!rsCreds.next()) {
                    log.error(Constants.CREDENTALSNOTFOUND + rs.getInt(Constants.ID));
                    return Either.left(Constants.CUSTOMERDBERROR + Constants.CREDENTALSNOTFOUND + rs.getInt(Constants.ID));
                }
                Customer resultCustomer = new Customer(rs.getInt(Constants.ID), rs.getInt(Constants.PHONE), rs.getString(Constants.NAME), rs.getString(Constants.LASTNAME), rs.getString(Constants.EMAIL), new Credential(rsCreds.getString(Constants.USERNAME), rsCreds.getString(Constants.PASSWORD)), rs.getDate(Constants.BIRTHDATE).toLocalDate()

                );
                customers.add(resultCustomer);
            }
            return Either.right(customers);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.CUSTOMERDBERROR + e.getMessage());
        }
    }

    public Either<String, Customer> get(int id) {
        try (Connection con = db.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Either.right(new Customer(rs.getInt(Constants.ID), rs.getInt(Constants.PHONE), rs.getString(Constants.NAME), rs.getString(Constants.LASTNAME), rs.getString(Constants.EMAIL), new Credential("", ""), rs.getDate(Constants.BIRTHDATE).toLocalDate()));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return Either.left(Constants.CUSTOMERDBERROR + ex.getMessage());
        }
    }

    @Override
    public int save(Customer c) {
        try (Connection con = db.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.ADD_CUSTOMER_QUERY); PreparedStatement credentials = con.prepareStatement(SQLConstants.ADD_CREDENTIALS_QUERY)) {
            //(id, first_name, last_name, email, phone, date_of_birth)
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
            return -1;
        }
    }

    @Override
    public int modify(Customer initialcustomer, Customer modifiedcustomer) {
        int deletionResult = delete(initialcustomer);
        if (deletionResult != -1) {
            return save(modifiedcustomer);
        } else {
            return -1;
        }
    }

    public boolean hasOrders(Customer c) {
        try (Connection con = db.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.CHECK_ORDERS_BY_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, c.getId());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            log.info("the customer named " + c.getName() + " has " + rs.getInt("order_count") + " orders");
            return rs.getInt(Constants.COUNT) > 0;
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }


    @Override
    public int delete(Customer c) {
        if (c == null) {
            return -1;
        }
        try (Connection con = db.getConnection()) {
            borrarOrderyOrderItems(c, con);
            return borrarCustomer(c, con);
        } catch (SQLException ex) {
            log.error(Constants.ERRDB + ex.getMessage());
            return -1;
        }
    }

    private int borrarCustomer(Customer c, Connection con) throws SQLException {
        try (PreparedStatement deleteCustomer = con.prepareStatement(SQLConstants.DELETE_CUSTOMER_QUERY); PreparedStatement deleteCredentials = con.prepareStatement(SQLConstants.DELETE_CREDENTIALS_QUERY)) {
            deleteCustomer.setInt(1, c.getId());
            deleteCredentials.setInt(1, c.getId());
            deleteCredentials.executeUpdate();
            deleteCustomer.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            log.error(Constants.ERRDELETECUSTOMER + ex.getMessage());
            con.rollback();
            return -1;
        }
    }

    private void borrarOrderyOrderItems(Customer c, Connection con) throws SQLException {
        try (PreparedStatement deleteOrderItems = con.prepareStatement(SQLConstants.DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY); PreparedStatement deleteOrders = con.prepareStatement(SQLConstants.DELETE_ORDERS_BY_CUSTOMER_QUERY)) {
            con.setAutoCommit(false); // Start a transaction
            deleteOrderItems.setInt(1, c.getId());
            deleteOrders.setInt(1, c.getId());

            deleteOrderItems.executeUpdate();
            deleteOrders.executeUpdate();

            con.commit();
        } catch (SQLException ex) {
            log.error(Constants.ERRDELETEVARIOUS + ex.getMessage());
            con.rollback();
        }
    }

    public int findIdFromUsername(String username) {
        try (Connection con = db.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_CUSTOMER_ID_FROM_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(Constants.CUSID);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return -1;
        }
    }
}
