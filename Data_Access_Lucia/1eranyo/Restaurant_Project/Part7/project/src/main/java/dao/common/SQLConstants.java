package dao.common;

public class SQLConstants {
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
    public static final String SELECT_ORDER_ITEMS_QUERY = "SELECT * FROM order_items";
    public static final String SELECT_ORDER_ITEM_QUERY = "SELECT * FROM order_items WHERE order_item_id = ?";
    public static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_items (order_item_id, order_id, menu_item_id, quantity) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_ORDER_ITEM_QUERY = "UPDATE order_items SET order_id = ?, menu_item_id = ?, quantity = ? WHERE order_item_id = ?";
    public static final String DELETE_ORDER_ITEM_QUERY = "DELETE FROM order_items WHERE order_item_id = ?";
    public static final String SELECT_CUSTOMER_QUERY = "SELECT * FROM customers WHERE id = ?";
    public static final String ADD_CUSTOMER_QUERY = "INSERT INTO customers (id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DELETE_MENU_ITEM_QUERY = "DELETE FROM menu_items WHERE menu_item_id = ?";
    public static final String UPDATE_MENU_ITEM_QUERY = "UPDATE menu_items SET name = ?, description = ?, price = ? WHERE menu_item_id = ?";
    public static final String INSERT_MENU_ITEM_QUERY = "INSERT INTO menu_items (menu_item_id, name, description, price) VALUES (?, ?, ?, ?)";
    public static final String SELECT_MENU_ITEM_QUERY = "SELECT * FROM menu_items WHERE menu_item_id = ?";
    public static final String SELECT_MENU_ITEMS_QUERY = "SELECT * FROM menu_items";

    public static final String SELECT_CUSTOMER_ID_FROM_USERNAME_QUERY = "SELECT customer_id FROM credentials WHERE username = ?";
    public static final String SELECT_CREDS_FROM_CUSTOMER_QUERY = "SELECT * FROM credentials WHERE customer_id = ?";
    public static final String ADD_CREDENTIALS_QUERY =  "INSERT INTO credentials (customer_id, username, password) VALUES (?, ?, ?)";
    public static final String SELECT_CREDENTIALS_BY_CUSTOMER_ID = "SELECT user_name, password FROM credentials WHERE customer_id = ?";


}
