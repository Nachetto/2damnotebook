package dao.common;

public class SQLConstants {
    public static final String URL = "urlDB";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DRIVER_CLASS_NAME = "driver";
    public static final String CACHE = "cachePrepStmts";
    public static final String CACHESIZE = "prepStmtCacheSize";
    public static final String CACHELIMIT = "prepStmtCacheSqlLimit";

    private SQLConstants() {}
    public static final String SELECT_CUSTOMERS_QUERY = "SELECT * FROM customers";
    public static final String DELETE_ORDERS_BY_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY = "DELETE FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE customer_id = ?)";
    public static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customers WHERE id = ?";
    public static final String CHECK_ORDERS_BY_CUSTOMER_QUERY = "SELECT COUNT(*) as order_count FROM orders WHERE customer_id = ?";
    public static final String SELECT_ORDERS_QUERY = "SELECT * FROM orders";
    public static final String SELECT_ORDER_QUERY = "SELECT * FROM orders WHERE order_id = ?";
    public static final String INSERT_ORDER_QUERY = "INSERT INTO orders (order_id, order_date, customer_id, table_id) VALUES (?, ?, ?, ?)";
    public static final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE order_id = ?";
    public static final String DELETE_ORDER_FROM_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_CREDENTIALS_QUERY = "DELETE FROM credentials WHERE customer_id = ?";
    public static final String SELECT_CUSTOMER_QUERY = "SELECT * FROM customers WHERE id = ?";
    public static final String ADD_CUSTOMER_QUERY = "INSERT INTO customers (id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_CUSTOMER_ID_FROM_USERNAME_QUERY = "SELECT customer_id FROM credentials WHERE user_name = ?";
    public static final String SELECT_CREDS_FROM_CUSTOMER_QUERY = "SELECT * FROM credentials WHERE customer_id = ?";
    public static final String ADD_CREDENTIALS_QUERY =  "INSERT INTO credentials (customer_id, user_name, password) VALUES (?, ?, ?)";
}
