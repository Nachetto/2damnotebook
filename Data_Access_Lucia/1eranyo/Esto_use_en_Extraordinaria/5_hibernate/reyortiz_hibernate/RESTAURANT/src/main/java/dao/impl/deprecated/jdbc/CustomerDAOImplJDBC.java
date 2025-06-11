package dao.impl.deprecated.jdbc;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.common.connections.DBConnectionPool;
import dao.common.SqlQueries;
import dao.CustomerDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Credentials;
import model.Customer;
import service.CredentialsServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

//@Log4j2
//public class CustomerDAOImplJDBC implements CustomerDAO {
//    private final DBConnectionPool dbConnection;
//    private final CredentialsServices credentialsServices;
//
//    @Inject
//    public CustomerDAOImplJDBC(DBConnectionPool dbConnection, CredentialsServices credentialsServices) {
//        this.dbConnection = dbConnection;
//        this.credentialsServices = credentialsServices;
//    }
//
//    @Override
//    public Either<RestaurantError, List<Customer>> getAll() {
//        Either<RestaurantError, List<Customer>> either;
//
//        try (Connection myConnection = dbConnection.getConnection();
//             Statement statement = myConnection.createStatement()) {
//            ResultSet rs = statement.executeQuery(SqlQueries.CUSTOMERGETALL);
//
//            Either<RestaurantError, List<Customer>> read = readRS(rs);
//            if (read.isRight()) {
//                either = Either.right(read.get());
//            } else {
//                either = Either.left(read.getLeft());
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Customer> get(int id) {
//        Either<RestaurantError, Customer> either;
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.CUSTOMERGET)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            Either<RestaurantError, List<Customer>> read = readRS(resultSet);
//            if (read.isRight()) {
//                either = Either.right(read.get().get(0));
//            } else {
//                either = Either.left(read.getLeft());
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> add(Customer customer) {
//        Either<RestaurantError, Integer> either;
//
//        try (Connection connection = dbConnection.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.CUSTOMERADD);
//                 PreparedStatement preparedStatementCredentials = connection.prepareStatement(SqlQueries.CREDENTIALSADD, Statement.RETURN_GENERATED_KEYS)) {
//
//                preparedStatementCredentials.setString(1, customer.getCredentials().getUsername());
//                preparedStatementCredentials.setString(2, customer.getCredentials().getPassword());
//
//                connection.setAutoCommit(false);
//                preparedStatementCredentials.executeUpdate();
//
//                ResultSet rs = preparedStatementCredentials.getGeneratedKeys();
//                if (rs.next()) {
//                    int autoId = rs.getInt(1);
//                    customer.setIdcustomer(autoId);
//                }
//
//                preparedStatement.setInt(1, customer.getIdcustomer());
//                preparedStatement.setString(2, customer.getFirstName());
//                preparedStatement.setString(3, customer.getLastName());
//                preparedStatement.setString(4, customer.getEmail());
//                preparedStatement.setString(5, customer.getPhone());
//                preparedStatement.setDate(6, Date.valueOf(customer.getDateOfBirth()));
//
//                int result = preparedStatement.executeUpdate();
//                if (result > 0) {
//                    either = Either.right(result);
//                } else {
//                    either = Either.left(new RestaurantError(0, UtilitiesCommon.ADDERROR));
//                }
//
//            } catch (SQLException e) {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    either = Either.left(new RestaurantError(0, e.getMessage()));
//                }
//            } finally {
//                connection.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> update(Customer customer) {
//        Either<RestaurantError, Integer> either;
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement preparedStatementCustomer = connection.prepareStatement(SqlQueries.CUSTOMERUPDATE)) {
//            preparedStatementCustomer.setString(1, customer.getFirstName());
//            preparedStatementCustomer.setString(2, customer.getLastName());
//            preparedStatementCustomer.setString(3, customer.getEmail());
//            preparedStatementCustomer.setString(4, customer.getPhone());
//            preparedStatementCustomer.setDate(5, Date.valueOf(customer.getDateOfBirth()));
//            preparedStatementCustomer.setInt(6, customer.getIdcustomer());
//
//
//            int result = preparedStatementCustomer.executeUpdate();
//
//            if (result > 0) {
//                either = Either.right(result);
//            } else {
//                either = Either.left(new RestaurantError(result, UtilitiesCommon.UPDATERROR));
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> delete(Customer customer, boolean confirm) {
//        Either<RestaurantError, Integer> either;
//        try (Connection connection = dbConnection.getConnection()) {
//            if (confirm) {
//                either = deleteWithOrders(customer, connection);
//            } else {
//                either = deleteWithoutOrders(customer, connection);
//            }
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithoutOrders(Customer customer, Connection connection) {
//        Either<RestaurantError, Integer> either;
//
//        try (PreparedStatement preparedStatementCredentials = connection.prepareStatement(SqlQueries.CREDENTIALSDELETE);
//             PreparedStatement preparedStatementCustomer = connection.prepareStatement(SqlQueries.CUSTOMERDELETE)) {
//
//            preparedStatementCredentials.setInt(1, customer.getIdcustomer());
//            preparedStatementCustomer.setInt(1, customer.getIdcustomer());
//
//            connection.setAutoCommit(false);
//            int customerResult = preparedStatementCustomer.executeUpdate();
//            preparedStatementCredentials.executeUpdate();
//
//            connection.commit();
//            either = Either.right(customerResult);
//
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 1451) {
//                either = Either.left(new RestaurantError(0, UtilitiesCommon.CONFDELETE));
//            } else {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//            }
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                either = Either.left(new RestaurantError(0, ex.getMessage()));
//            }
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithOrders(Customer customer, Connection connection) {
//        Either<RestaurantError, Integer> either;
//        try (PreparedStatement preparedStatementItems = connection.prepareStatement(SqlQueries.ORDERITEMDELETEBYCUSTOMER);
//             PreparedStatement preparedStatementOrders = connection.prepareStatement(SqlQueries.ORDERDELETEBYCUSTOMER);
//             PreparedStatement preparedStatementCredentials = connection.prepareStatement(SqlQueries.CREDENTIALSDELETE);
//             PreparedStatement preparedStatementCustomer = connection.prepareStatement(SqlQueries.CUSTOMERDELETE)) {
//
//            preparedStatementItems.setInt(1, customer.getIdcustomer());
//            preparedStatementOrders.setInt(1, customer.getIdcustomer());
//            preparedStatementCustomer.setInt(1, customer.getIdcustomer());
//            preparedStatementCredentials.setInt(1, customer.getIdcustomer());
//
//
//            //deleting items and orders
//            connection.setAutoCommit(false);
//            preparedStatementItems.executeUpdate();
//            preparedStatementOrders.executeUpdate();
//            int result = preparedStatementCustomer.executeUpdate();
//            preparedStatementCredentials.executeUpdate();
//
//            connection.commit();
//
//            if (result > 0) {
//                either = Either.right(result);
//            } else {
//                either = Either.left(new RestaurantError(result, UtilitiesCommon.DELETERROR));
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                either = Either.left(new RestaurantError(0, ex.getMessage()));
//            }
//        }
//
//        return either;
//    }
//
//    private Either<RestaurantError, List<Customer>> readRS(ResultSet rs) {
//        Either<RestaurantError, List<Customer>> either;
//        List<Customer> list = new ArrayList<>();
//
//        try {
//            while (rs.next()) {
//                int id = rs.getInt(SqlQueries.IDCUSTOMER);
//                String firstName = rs.getString(SqlQueries.FIRST_NAME);
//                String lastName = rs.getString(SqlQueries.LAST_NAME);
//                String email = rs.getString(SqlQueries.EMAIL);
//                String phone = rs.getString(SqlQueries.PHONE);
//                LocalDate date = rs.getDate(SqlQueries.DATE_OF_BIRTH).toLocalDate();
//                Either<RestaurantError, Credentials> credentials = credentialsServices.get(id);
//                if (credentials.isRight()) {
//                    list.add(new Customer(id, firstName, lastName, email, phone, date, credentials.get()));
//                } else {
//                    throw new SQLException(credentials.getLeft().getMessage());
//                }
//            }
//            either = Either.right(list);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//        return either;
//    }
//
//}
