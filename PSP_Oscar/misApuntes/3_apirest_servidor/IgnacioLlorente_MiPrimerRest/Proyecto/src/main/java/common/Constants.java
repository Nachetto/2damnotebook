package common;

public class Constants {
    public static final String ERRDBCLOSE = "There was an error while trying to close the database connection";
    public static final String ERRDELETECUSTOMER = "Error deleting customer: ";
    public static final String CREDENTALSNOTFOUND = "No credentials found for customer with id ";
    public static final String ID = "id";
    public static final String ERRDELETEVARIOUS = "Error deleting associated orders and items: ";
    public static final String PHONE = "phone";
    public static final String NAME = "first_name";
    public static final String LASTNAME = "last_name";
    public static final String EMAIL = "email";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String BIRTHDATE = "date_of_birth";
    public static final String COUNT = "order_count";
    public static final String ORDERID = "order_id" ;
    public static final String TABLEID ="table_id" ;
    public static final String ORDERDATE = "order_date";

    public static final String CUSID = "customer_id";
    public static final String ORDERDBERROR = "There was an error while reading Orders Database: ";
    public static final String CUSTOMERDBERROR = "Error al leer los datos de los clientes: ";
    public static final String ERRDB = "There was an error while trying to connect to the database supplier\n";
    public static final String IDNOTFOUND = "The requested id has not found any item";
    public static final String ERROROBTAININGORDER = "There was an error while trying to obtain the order: ";
    public static final String CONFIG_FILE = "properties.xml";
    private Constants() {
    }
}
