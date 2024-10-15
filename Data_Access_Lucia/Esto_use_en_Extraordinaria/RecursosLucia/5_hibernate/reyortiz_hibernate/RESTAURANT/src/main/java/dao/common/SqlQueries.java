package dao.common;

public class SqlQueries {

    public static final String IDCREDENTIALS = "id";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String CUSTOMER = "customer";
    public static final String CREDENTIALS = "credentials";
    public static final String INTEGRITY_CONSTRAINT_VIOLATION = "IntegrityConstraintViolation";
    public static final String HQL_GET_ALL_CUSTOMERS = "HQL_GET_ALL_CUSTOMERS";
    public static final String FROM_CUSTOMER = "from Customer";
    public static final String UNIT_3_HIBERNATE = "unit3.hibernate";

    private SqlQueries(){}



    //CUSTOMER
    public static final String CUSTOMERGETALL = "SELECT * FROM customer";
    public static final String CUSTOMERGETALLWITHCREDENTIALS = "SELECT * FROM customer inner join credentials on customer.idcustomer = credentials.id";
    public static final String CUSTOMERGET = "SELECT * FROM customer WHERE idcustomer = ?";
    public static final String CUSTOMERGETWITHCREDENTIALS = "SELECT * FROM customer join credentials on customer.idcustomer = credentials.id WHERE idcustomer = 1";
    public static final String CUSTOMERUPDATE = "update customer set first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ? where idcustomer = ?";
    public static final String CUSTOMERUPDATEWITHCREDENTIALS = "update customer cu inner join credentials cr on (cu.idcustomer = cr.id) set cu.first_name = ?, cu.last_name = ?, cu.email = ?, cu.phone = ?, cu.date_of_birth = ?, cr.user_name = ?, cr.password = ? where cu.idcustomer = ? and cr.id = ?";
    public static final String CUSTOMERADD = "INSERT INTO customer (idcustomer, first_name, last_name, email, phone, date_of_birth) VALUES (?,?,?,?,?,?)";
    public static final String CUSTOMERDELETE = "delete from customer where idcustomer = ?";




    //ORDER

    public static final String ORDERGETALL = "SELECT * FROM restaurant_order";
    public static final String ORDERGET = "SELECT * FROM restaurant_order WHERE idorder = ?";
    public static final String ORDERGETBYCUSTOMER = "SELECT * FROM restaurant_order WHERE idcustomer = ?";
    public static final String ORDERUPDATE = "update restaurant_order set table_number = ?, idcustomer = ? where idorder = ?";
    public static final String ORDERADD = "INSERT INTO restaurant_order (table_number, idcustomer, time_stamp) VALUES (?,?,?)";
    public static final String ORDERDELETE = "delete from restaurant_order where idorder = ?";
    public static final String ORDERDELETEBYCUSTOMER = "delete from restaurant_order where idcustomer = ?";

    //ORDERITEM
    public static final String ORDERITEMGETBYORDER = "SELECT * FROM order_item WHERE idorder = ?";
    public static final String ORDERITEMGETWITHMENUITEM = "SELECT * FROM order_item oi inner join menu_item mi on oi.idmenu_item = mi.idmenu_item where oi.idorder = ?";
    public static final String ORDERITEMUPDATE = "update order_item set idmenu_item = ?, quantity = ? where idorder_item = ?";
    public static final String ORDERITEMADD = "INSERT INTO order_item (idorder, idmenu_item, quantity) VALUES (?,?,?)";
    public static final String ORDERITEMDELETEBYCUSTOMER = "delete from order_item where idorder in (select idorder from restaurant_order where idcustomer = ?)";
    public static final String ORDERITEMDELETEBYORDER = "delete from order_item where idorder = ?";


    //MENUITEM
    public static final String MENUITEMGETALL = "SELECT * FROM menu_item";
    public static final String MENUITEMGET = "SELECT * FROM menu_item WHERE idmenu_item = ?";

    //CREDENTIALS
    public static final String CREDENTIALSGETALL = "SELECT * FROM credentials";
    public static final String CREDENTIALSGET = "SELECT * FROM credentials WHERE id = ?";
    public static final String CREDENTIALSADD = "INSERT INTO credentials (user_name, password) VALUES (?,?)";
    public static final String CREDENTIALSUPDATE = "update credentials set user_name = ?, password = ? where id = ?";
    public static final String CREDENTIALSDELETE = "delete from credentials where id = ?";

    //Connection pool
    public static final String URL = "sqlUrl";
    public static final String USER = "sqlUser";
    public static final String SQL_PASSWORD = "sqlPassword";

    public static final String URLCASA = "sqlUrlCasa";
    public static final String USERCASA = "sqlUserCasa";
    public static final String SQL_PASSWORD_CASA = "sqlPasswordCasa";
    public static final String DRIVER = "driver";
    public static final String HIKARICACHEPREP = "cachePrepStmts";
    public static final String HIKARICACHESIZE= "prepStmtCacheSize";
    public static final String HIKARICACHELIMIT = "prepStmtCacheSqlLimit";

    //TABLE NAMES
    public static final String IDORDER = "idorder";
    public static final String TABLE_NUMBER = "table_number";
    public static final String IDCUSTOMER = "idcustomer";
    public static final String TIME_STAMP = "time_stamp";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String IDORDER_ITEM = "idorder_item";
    public static final String IDMENU_ITEM = "idmenu_item";
    public static final String QUANTITY = "quantity";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
}
