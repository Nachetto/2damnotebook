package dao.common;

public class SQLConstants {
    public static final String SELECT_customers_QUERY="SELECT * FROM customers";
    public static final String DELETE_ORDERS_BY_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY = "DELETE FROM order_items WHERE order_id In (SELECT order_id FROM orders WHERE customer_id = ?)";
    public static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customers WHERE id = ?";
    public static final String CHECK_ORDERS_BY_CUSTOMER_QUERY = "SELECT COUNT(*) as order_count FROM orders WHERE customer_id = ?;";
    public static final String SELECT_ORDERS_QUERY = "SELECT * FROM orders";
    public static final String SELECT_ORDER_QUERY = "SELECT * FROM orders WHERE order_id = ?";
    public static final String INSERT_ORDER_QUERY = "INSERT INTO orders (order_id, order_date, customer_id, table_id) VALUES (?, ?, ?, ?)";
    public static final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE order_id = ?";
    public static final String DELETE_ORDER_FROM_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_CREDENTIALS_QUERY = "DELETE FROM credentials WHERE customer_id = ?";
    public static String SELECT_customer_QUERY="SELECT * FROM customers WHERE id = ?";
    public static String Add_customer_QUERY="INSERT INTO customers (id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
}
