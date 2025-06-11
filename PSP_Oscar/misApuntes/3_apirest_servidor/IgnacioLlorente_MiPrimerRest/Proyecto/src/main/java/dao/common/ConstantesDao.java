package dao.common;

public class ConstantesDao {

    public static final String OBTENER_TODAS_LAS_ORDENES = "Error al obtener todas las órdenes: ";
    public static final String NO_ENCONTRADA_CON_ID = "Orden no encontrada con ID: ";
    public static final String ORDEN_A_ELIMINAR_ES_NULA = "Orden a eliminar es nula";
    public static final String ORDEN_A_MODIFICAR_ES_NULA = "Orden a modificar es nula";
    public static final String ERROR_AL_MODIFICAR_LA_ORDEN_CON_ID = "Error al modificar la orden con ID: ";
    public static final String ERROR_AL_ELIMINAR_LA_ORDEN = "Error al eliminar la orden: ";
    public static final String ERROR_AL_GUARDAR_LA_ORDEN = "Error al guardar la orden: ";
    public static final String ID_DE_ORDEN_NO_VALIDO = "Orden o ID de orden no válido";
    public static final String OBTENER_LA_ORDEN_CON_ID = "Error al obtener la orden con ID: ";

    private ConstantesDao() {}
    public static final String CLIENTE_NO_ENCONTRADO_CON_ID = "Cliente no encontrado con ID: ";
    public static final String ERROR_AL_OBTENER_TODOS_LOS_CLIENTES = "Error al obtener todos los clientes: ";
    public static final String ERROR_AL_OBTENER_EL_CLIENTE_CON_ID = "Error al obtener el cliente con ID: ";
    public static final String INVALIDID = "ID no válido: ";
    public static final String EXISTE_CON_ID = "Cliente ya existe con ID: ";
    public static final String ERROR_AL_GUARDAR_EL_CLIENTE = "Error al guardar el cliente: ";
    public static final String ES_NULO = "Cliente proporcionado para modificar es nulo";
    public static final String ERROR_AL_MODIFICAR_EL_CLIENTE_CON_ID = "Error al modificar el cliente con ID: ";
    public static final String ID_DEL_CLIENTE_NO_VALIDO_O_NULO = "ID del cliente no válido o cliente nulo para comprobar órdenes";
    public static final String ERROR_AL_ELIMINAR_EL_CLIENTE = "Error al eliminar el cliente: ";
    public static final String ELIMINAR_ES_NULO = "Cliente a eliminar es nulo";
    public static final String VERIFICAR_ORDENES_DEL_CLIENTE = "Error al verificar órdenes del cliente: ";




    public static final String URL = "urlDB";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DRIVER_CLASS_NAME = "driver";
    public static final String CACHE = "cachePrepStmts";
    public static final String CACHESIZE = "prepStmtCacheSize";
    public static final String CACHELIMIT = "prepStmtCacheSqlLimit";



    public static final String CHECK_ORDER_EXISTENCE_QUERY = "SELECT COUNT(*) as order_count FROM orders WHERE order_id = ?";
    public static final String CHECK_CUSTOMER_EXISTENCE_QUERY = "SELECT COUNT(*) as customer_count FROM customers WHERE id = ?";
    public static final String SELECT_CUSTOMERS_QUERY = "SELECT * FROM customers";

    public static final String DELETE_ORDERS_BY_CUSTOMER_QUERY = "DELETE FROM orders WHERE customer_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_CUSTOMER_QUERY = "DELETE FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE customer_id = ?)";

    public static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customers WHERE id = ?";
    public static final String CHECK_ORDERS_BY_CUSTOMER_QUERY = "SELECT COUNT(*) as order_count FROM orders WHERE customer_id = ?";
    public static final String SELECT_ORDERS_QUERY = "SELECT * FROM orders";
    public static final String SELECT_ORDER_QUERY = "SELECT * FROM orders WHERE order_id = ?";
    public static final String INSERT_ORDER_QUERY = "INSERT INTO orders (order_id, order_date, customer_id, table_id) VALUES (?, ?, ?, ?)";
    public static final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE order_id = ?";
    public static final String DELETE_CREDENTIALS_QUERY = "DELETE FROM credentials WHERE customer_id = ?";
    public static final String SELECT_CUSTOMER_QUERY = "SELECT * FROM customers WHERE id = ?";
    public static final String ADD_CUSTOMER_QUERY = "INSERT INTO customers (id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_CREDS_FROM_CUSTOMER_QUERY = "SELECT * FROM credentials WHERE customer_id = ?";
    public static final String ADD_CREDENTIALS_QUERY =  "INSERT INTO credentials (customer_id, user_name, password) VALUES (?, ?, ?)";

}
