package JDBCCoffeeExample.utils;

public class SQLQueries {
    public static final String SELECT_coffees_QUERY = "select * from coffees";
    public static final String SELECT_coffee_QUERY = "select * from coffees where COF_NAME= ?";
    public static final String UPDATE_SALES_coffees = "update coffees set SALES = ? where COF_NAME = ?";
    public static final String UPDATE_TOTAL_COFFEE = "update coffees set TOTAL = TOTAL + ? where COF_NAME = ?";
    public static final String INSERT_COFFEE = "INSERT INTO coffees (id_prod, COF_NAME, SUPP_ID, PRICE, SALES, TOTAL) VALUES (?,?,?, ?, ?, ?)";

    public static final String INSERT_COFFEE_WITH_AUTOINCREMENTAL_ID = "INSERT INTO coffees (COF_NAME, SUPP_ID, PRICE, SALES, TOTAL) VALUES (?,?,?, ?, ?, ?)";

    public static final String DELETE_COFFEE = "delete from coffees where id_prod = ?";
    }
