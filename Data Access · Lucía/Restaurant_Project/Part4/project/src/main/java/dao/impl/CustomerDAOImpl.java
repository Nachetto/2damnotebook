package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.CustomerDAO;
import dao.common.DBConnection;
import dao.common.SQLConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class CustomerDAOImpl implements CustomerDAO {

    private final DBConnection db;

    @Inject
    public CustomerDAOImpl(DBConnection db) {
        this.db = db;
    }

    public Either<String, List<Customer>> getAll() {
        try (Connection myConnection = DriverManager.getConnection("jdbc:mysql://dam2.mysql.iesquevedo.es: 3335/ignacioLlorente_restaurant", "root", "quevedo2dam");
             Statement stmt = myConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_customers_QUERY);
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer resultCustomer = new Customer(
                        rs.getInt("id"),
                        rs.getInt("phone"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        new Credential("root", "2dam"),
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
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_customer_QUERY)) {
            preparedStatement.setInt(1, id);

            // Executing the statement. The result will be stored in the ResultSet object
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
        return 0;
    }

    @Override
    public int modify(Customer initialcustomer, Customer modifiedcustomer) {
        delete(initialcustomer);
        return 1;
    }

    @Override
    public int delete(Customer c) {
        if (c == null) {
            return -1;
        }

        try (Connection con = db.getConnection()) {
            try {
                // Check if the customer has any associated orders
                PreparedStatement checkOrders = con.prepareStatement(SQLConstants.CHECK_ORDERS_BY_CUSTOMER_QUERY);
                checkOrders.setInt(1, c.getId());
                ResultSet orderCheckResult = checkOrders.executeQuery();

                if (orderCheckResult.next()) {
                    // Customer has orders, ask the user for confirmation to delete them
                    System.out.println("This customer has associated orders. Do you want to delete them as well? (Y/N)");
                    // You can add user interaction here to confirm the deletion of orders

                    // If user confirms, proceed to delete associated orders and order items
                    if (userConfirmedDeletion) {
                        try (PreparedStatement deleteOrders = con.prepareStatement(SQLConstants.DELETE_ORDERS_BY_CUSTOMER_QUERY);
                             PreparedStatement deleteOrderItems = con.prepareStatement(SQLConstants.DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY)) {
                            con.setAutoCommit(false); // Start a transaction
                            deleteOrders.setInt(1, c.getId());
                            deleteOrderItems.setInt(1, c.getId());

                            // Execute the delete statements for orders and order items
                            deleteOrderItems.executeUpdate();
                            deleteOrders.executeUpdate();

                            con.commit(); // Commit the transaction
                        }
                    } else {
                        System.out.println("Deletion of customer and associated orders canceled.");
                        return 0; // Deletion canceled
                    }
                }

                // After deleting associated orders and order items (if applicable), you can delete the customer
                try (PreparedStatement deleteCustomer = con.prepareStatement(SQLConstants.DELETE_CUSTOMER_QUERY)) {
                    deleteCustomer.setInt(1, c.getId());
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




}
