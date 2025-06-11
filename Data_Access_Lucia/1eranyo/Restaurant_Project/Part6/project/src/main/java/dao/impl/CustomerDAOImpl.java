package dao.impl;

import common.Constants;
import dao.CustomerDAO;
import dao.common.DBConnection;
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
import java.util.Objects;

@Log4j2
public class CustomerDAOImpl implements CustomerDAO {

    private final DBConnection db;
    @Getter
    @Setter
    private boolean userConfirmedDeletion = false;

    @Inject
    public CustomerDAOImpl(DBConnection db) {
        this.db = db;
    }

    public Either<String, List<Customer>> getAll() {
        try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://dam2.mysql.wompwomp.com: 3335/ignacioLlorente_restaurant", "root", "quevedo2dam");
             Statement stmt = myConnection.createStatement();
             PreparedStatement preparedStatement = myConnection.prepareStatement(SQLConstants.SELECT_CREDS_FROM_CUSTOMER_QUERY)) {
            ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_CUSTOMERS_QUERY);
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt("id"));
                ResultSet rsCreds = preparedStatement.executeQuery();
                if (!rsCreds.next()) {
                    log.error("No credentials found for customer with id " + rs.getInt("id"));
                    return Either.left(Constants.CUSTOMERDBERROR + "No credentials found for customer with id " + rs.getInt("id"));
                }
                Customer resultCustomer = new Customer(
                        rs.getInt("id"),
                        rs.getInt("phone"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        new Credential(
                                rsCreds.getString("user_name"),
                                rsCreds.getString("password")
                        ),
                        rs.getDate("date_of_birth").toLocalDate()

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
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Either.right(new Customer(
                    rs.getInt("id"),
                    rs.getInt("phone"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    new Credential("root", "2dam"),
                    rs.getDate("date_of_birth").toLocalDate()
            ));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return Either.left(Constants.CUSTOMERDBERROR + ex.getMessage());
        }
    }

    public boolean checkLogin(Credential c) {
        Either<String, List<Customer>> result = getAll();

        if (result.isRight()) {
            List<Customer> customers = result.get();
            for (Customer cus : customers) {
                if (Objects.equals(cus.getCredential(), c)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int save(Customer c) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.ADD_CUSTOMER_QUERY);
             PreparedStatement credentials = con.prepareStatement(SQLConstants.ADD_CREDENTIALS_QUERY)) {
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
            if (preparedStatement.executeUpdate()!=-1){
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
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.CHECK_ORDERS_BY_CUSTOMER_QUERY)) {
            preparedStatement.setInt(1, c.getId());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            log.info("the customer named " + c.getName() + " has " + rs.getInt("order_count") + " orders");
            return rs.getInt("order_count") > 0;
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
            try {
                if (userConfirmedDeletion) {
                    try (
                            PreparedStatement deleteOrderItems = con.prepareStatement(SQLConstants.DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY);
                            PreparedStatement deleteOrders = con.prepareStatement(SQLConstants.DELETE_ORDERS_BY_CUSTOMER_QUERY)) {
                        con.setAutoCommit(false); // Start a transaction
                        deleteOrderItems.setInt(1, c.getId());
                        deleteOrders.setInt(1, c.getId());

                        // Execute the delete statements for orders and order items
                        deleteOrderItems.executeUpdate();
                        deleteOrders.executeUpdate();

                        con.commit(); // Commit the transaction
                    }
                }

                try (PreparedStatement deleteCustomer = con.prepareStatement(SQLConstants.DELETE_CUSTOMER_QUERY);
                     PreparedStatement deleteCredentials = con.prepareStatement(SQLConstants.DELETE_CREDENTIALS_QUERY)) {
                    deleteCustomer.setInt(1, c.getId());
                    deleteCredentials.setInt(1, c.getId());
                    deleteCredentials.executeUpdate();
                    deleteCustomer.executeUpdate();
                    return 1; // Deletion successful
                } catch (SQLException ex) {
                    log.error("Error deleting customer: " + ex.getMessage());
                    con.rollback(); // Rollback in case of an exception during customer deletion
                    return -1;
                }
            } catch (SQLException ex) {
                log.error("Error deleting associated orders and items: " + ex.getMessage());
                con.rollback(); // Rollback in case of an exception during orders and order items deletion
                return -1;
            }
        } catch (SQLException ex) {
            log.error("Error connecting to the database: " + ex.getMessage());
            return -1;
        }
    }


    public int findIdFromUsername(String username) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_CUSTOMER_ID_FROM_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt("customer_id");
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return -1;
        }
    }
}
