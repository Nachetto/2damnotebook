package dao.impl.deprecated.spring;

//import common.RestaurantError;
//import common.UtilitiesCommon;
//import dao.CustomerDAO;
//import dao.common.connections.DBConnectionPool;
//import dao.common.SqlQueries;
//import io.vavr.control.Either;
//import jakarta.inject.Inject;
//import model.Customer;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.transaction.TransactionDefinition;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//
//public class CustomerDAOImplSpring implements CustomerDAO {
//    private final DBConnectionPool dbConnection;
//
//    @Inject
//    public CustomerDAOImplSpring(DBConnectionPool dbConnection) {
//        this.dbConnection = dbConnection;
//    }
//
////    @Override
//    public Either<RestaurantError, List<Customer>> getAll() {
//        Either<RestaurantError, List<Customer>> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<Customer> customers = jdbcTemplate.query(SqlQueries.CUSTOMERGETALL, BeanPropertyRowMapper.newInstance(Customer.class));
//        either = customers.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR)) : Either.right(customers);
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Customer> get(int id) {
//        Either<RestaurantError, Customer> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<Customer> customers = jdbcTemplate.query(SqlQueries.CUSTOMERGET, BeanPropertyRowMapper.newInstance(Customer.class), id);
//        either = customers.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR)) : Either.right(customers.get(0));
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> add(Customer customer) {
//        Either<RestaurantError, Integer> either;
//        int result;
//        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//
//        try {
//            SimpleJdbcInsert jdbcInsertCredentials = new SimpleJdbcInsert(Objects.requireNonNull(transactionManager.getDataSource())).withTableName(SqlQueries.CREDENTIALS)
//                    .withTableName(SqlQueries.CREDENTIALS)
//                    .usingGeneratedKeyColumns(SqlQueries.IDCREDENTIALS);
//            Map<String, Object> paramCredentials = new HashMap<>();
//
//            paramCredentials.put(SqlQueries.USERNAME, customer.getCredentials().getUsername());
//            paramCredentials.put(SqlQueries.PASSWORD, customer.getCredentials().getPassword());
//
//            int num = (int) jdbcInsertCredentials.executeAndReturnKey(paramCredentials).longValue();
//            customer.getCredentials().setIdcredentials(num);
//            customer.setIdcustomer(num);
//
//            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(transactionManager.getDataSource())).withTableName(SqlQueries.CUSTOMER);
//            Map<String, Object> paramCustomer = new HashMap<>();
//
//            paramCustomer.put(SqlQueries.IDCUSTOMER, customer.getIdcustomer());
//            paramCustomer.put(SqlQueries.FIRST_NAME, customer.getFirstName());
//            paramCustomer.put(SqlQueries.LAST_NAME, customer.getLastName());
//            paramCustomer.put(SqlQueries.EMAIL, customer.getEmail());
//            paramCustomer.put(SqlQueries.PHONE, customer.getPhone());
//            paramCustomer.put(SqlQueries.DATE_OF_BIRTH, customer.getDateOfBirth());
//
//            result = jdbcInsert.execute(paramCustomer);
//
//            transactionManager.commit(transactionStatus);
//            either = result > 0 ? Either.right(result) : Either.left(new RestaurantError(0, UtilitiesCommon.ADDERROR));
//
//        } catch (Exception e) {
//            if(e.getMessage().contains(UtilitiesCommon.DUPLICATE_ENTRY)){
//                either = Either.left(new RestaurantError(0, UtilitiesCommon.THAT_USERNAME_IS_ALREADY_IN_USE));
//            } else {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//            }
//            transactionManager.rollback(transactionStatus);
//        }
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> update(Customer customer) {
//        Either<RestaurantError, Integer> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//
//        int result = jdbcTemplate.update(SqlQueries.CUSTOMERUPDATE,
//                customer.getFirstName(),
//                customer.getLastName(),
//                customer.getEmail(),
//                customer.getPhone(),
//                customer.getDateOfBirth(),
//                customer.getIdcustomer());
//        either = result > 0 ? Either.right(result) : Either.left(new RestaurantError(result, UtilitiesCommon.UPDATECUSTERROR));
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> delete(Customer customer, boolean confirm) {
//        Either<RestaurantError, Integer> either;
//
//        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//
//        if (!confirm) {
//            either = deleteWithoutOrders(customer, transactionManager, transactionStatus);
//        } else {
//            either = deleteWithOrders(customer, transactionManager, transactionStatus);
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithoutOrders(Customer customer, DataSourceTransactionManager transactionManager, TransactionStatus transactionStatus) {
//        Either<RestaurantError, Integer> either;
//        int result = 0;
//        try {
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
//            result += jdbcTemplate.update(SqlQueries.CUSTOMERDELETE, customer.getIdcustomer());
//            result += jdbcTemplate.update(SqlQueries.CREDENTIALSDELETE, customer.getIdcustomer());
//
//            transactionManager.commit(transactionStatus);
//            either = Either.right(result);
//
//        } catch (DataIntegrityViolationException e) {
//            transactionManager.rollback(transactionStatus);
//
//            if (Objects.requireNonNull(e.getMessage()).contains(SqlQueries.INTEGRITY_CONSTRAINT_VIOLATION)) {
//                either = Either.left(new RestaurantError(0, UtilitiesCommon.CONFDELETE));
//            } else {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//            }
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithOrders(Customer customer, DataSourceTransactionManager transactionManager, TransactionStatus transactionStatus) {
//        Either<RestaurantError, Integer> either;
//        int result = 0;
//        try {
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
//
//            result += jdbcTemplate.update(SqlQueries.ORDERITEMDELETEBYCUSTOMER, customer.getIdcustomer());
//            result += jdbcTemplate.update(SqlQueries.ORDERDELETEBYCUSTOMER, customer.getIdcustomer());
//            result += jdbcTemplate.update(SqlQueries.CUSTOMERDELETE, customer.getIdcustomer());
//            result += jdbcTemplate.update(SqlQueries.CREDENTIALSDELETE, customer.getIdcustomer());
//
//            transactionManager.commit(transactionStatus);
//            either = Either.right(result);
//
//        } catch (DataIntegrityViolationException e) {
//            transactionManager.rollback(transactionStatus);
//
//            if (Objects.requireNonNull(e.getMessage()).contains(SqlQueries.INTEGRITY_CONSTRAINT_VIOLATION)) {
//                either = Either.left(new RestaurantError(0, UtilitiesCommon.CONFDELETE));
//            } else {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//            }
//        }
//        return either;
//    }
//}
