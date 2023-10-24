package dao.common;

public class SQLConstants {
    public static final String SELECT_customers_QUERY="SELECT * FROM customers";
    public static final String DELETE_ORDERS_BY_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY = "DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE customer_id = ?)";
    public static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customers WHERE id = ?";
    public static final String CHECK_ORDERS_BY_CUSTOMER_QUERY = "SELECT COUNT(*) as order_count FROM orders WHERE customer_id = ?;";
    public static String SELECT_customer_QUERY="SELECT * FROM customers WHERE id = ?";
}
