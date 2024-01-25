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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.util.List;

@Log4j2
public class CustomerDAOImpl implements CustomerDAO {
    final DBConnection db;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = new BeanPropertyRowMapper<>(Customer.class);
    @Getter
    @Setter
    private boolean userConfirmedDeletion = false;

    @Inject
    public CustomerDAOImpl(DBConnection db) {
        this.db = db;
        jdbcTemplate = new JdbcTemplate(db.getDataSource());
    }

    public Either<String, List<Customer>> getAll() {
        try {
            String sql = SQLConstants.SELECT_CUSTOMERS_QUERY;
            List<Customer> customers = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Customer.class));
            customers.forEach(customer -> {
                Credential credential = getCredentialForCustomer(customer.getId());
                customer.setCredential(credential);
            });
            return Either.right(customers);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.CUSTOMERDBERROR + e.getMessage());
        }
    }


    public Either<String, Customer> get(int id) {
        try {
            String sql = SQLConstants.SELECT_CUSTOMER_QUERY;
            List<Customer> customerList = jdbcTemplate.query(
                    sql,
                    new Object[]{id},
                    customerRowMapper
            );
            if (customerList.isEmpty()) {
                return Either.left("No customer found with ID: " + id);
            } else {
                Customer customer = customerList.get(0);
                Credential credential = getCredentialForCustomer(id);
                if (credential != null) {
                    customer.setCredential(credential);
                }
                return Either.right(customer);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.CUSTOMERDBERROR + e.getMessage());
        }
    }


    private Credential getCredentialForCustomer(int customerId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQLConstants.SELECT_CREDENTIALS_BY_CUSTOMER_ID,
                    new Object[]{customerId}, // Make sure you are passing the customerId here.
                    (rs, rowNum) -> new Credential(
                            rs.getString("user_name"),
                            rs.getString("password")
                    )
            );
        } catch (DataAccessException e) {
            log.error("Error getting credentials for customer: " + e.getMessage(), e);
            return null;
        }
    }




    public boolean checkLogin(Credential c) {
        return getAll().getOrElse(List.of()).stream()
                .anyMatch(customer -> customer.getCredential() != null &&
                        customer.getCredential().username().equals(c.username()) &&
                        customer.getCredential().password().equals(c.password()));
    }

    @Override
    public int save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("phone", customer.getPhone());
        parameters.addValue("name", customer.getName());
        parameters.addValue("surname", customer.getSurname());
        parameters.addValue("email", customer.getEmail());
        parameters.addValue("birthdate", Date.valueOf(customer.getBirthdate()));

        int update = namedParameterJdbcTemplate.update(SQLConstants.ADD_CUSTOMER_QUERY, parameters, keyHolder, new String[]{"id"});

        if (update > 0 && keyHolder.getKey() != null) {
            int customerId = keyHolder.getKey().intValue();
            parameters = new MapSqlParameterSource();
            parameters.addValue("id", customerId);
            parameters.addValue("username", customer.getCredential().username());
            parameters.addValue("password", customer.getCredential().password());
            namedParameterJdbcTemplate.update(SQLConstants.ADD_CREDENTIALS_QUERY, parameters);
            return customerId;
        }
        return -1;
    }


    @Override
    public int modify(Customer initialCustomer, Customer modifiedCustomer) {
        int deleted = delete(initialCustomer);
        if (deleted == 1) {
            return save(modifiedCustomer);
        }
        return -1;
    }


    public boolean hasOrders(Customer customer) {
        if (customer == null) {
            return false;
        }
        try {
            String sql = SQLConstants.CHECK_ORDERS_BY_CUSTOMER_QUERY;
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", customer.getId());

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);

            return count != null && count > 0;
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }


    @Override
    public int delete(Customer customer) {
        if (customer == null) {
            return -1;
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", customer.getId());

        if (hasOrders(customer)) {
            namedParameterJdbcTemplate.update(SQLConstants.DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY, parameters);
            namedParameterJdbcTemplate.update(SQLConstants.DELETE_ORDERS_BY_CUSTOMER_QUERY, parameters);
        }
        return namedParameterJdbcTemplate.update(SQLConstants.DELETE_CUSTOMER_QUERY, parameters);
    }


    public int findIdFromUsername(String username) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);

        try {
            return namedParameterJdbcTemplate.queryForObject(SQLConstants.SELECT_CUSTOMER_ID_FROM_USERNAME_QUERY, parameters, Integer.class);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

}
