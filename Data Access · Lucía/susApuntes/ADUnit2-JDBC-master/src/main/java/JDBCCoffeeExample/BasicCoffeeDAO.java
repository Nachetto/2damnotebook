package JDBCCoffeeExample;

import JDBCCoffeeExample.utils.SQLQueries;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicCoffeeDAO {

    private DBConnection db;

    @Inject
    public BasicCoffeeDAO(DBConnection db) {
        this.db = db;
    }

    /**
     * Lists all coffees using Statement Class
     */
    public void getAll() {
//        stmt = null;
//        rs = null;
//        try {
//            // Open connection
//            con = pool.getConnection();
//            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(SQLQueries.SELECT_coffees_QUERY);
//            readRS(rs);
//        } catch (SQLException ex) {
//            Logger.getLogger(JDBCCoffeeExample.CoffeeDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            // db.releaseResource(rs);
//            // db.releaseResource(stmt); //Releasing the statement releases the Resultset
//            pool.closeConnection(con); //Closing the connection releases the statements
//        }
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_coffees_QUERY);
            readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(BasicCoffeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lists coffees of a given name
     */
    public void get(String c) {

        try (Connection con = db.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_coffee_QUERY)){
            preparedStatement.setString(1, c);

            // Executing the statement. The result will be stored in the ResultSet object
            ResultSet rs = preparedStatement.executeQuery();
            readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(BasicCoffeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Method for updating coffee sales using executeUpdate method
     */
    public void updateCoffeeSales(String coffee, int sales) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SALES_coffees)) {
            preparedStatement.setFloat(1, sales);
            preparedStatement.setString(2, coffee);

            // executeUpdate method for INSERT, UPDATE and DELETE
            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(BasicCoffeeDAO.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public int save(String coffeeName, int supplierID, float price,
                    int sales, int total) {
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_COFFEE)) {
            preparedStatement.setInt(1, 12);
            preparedStatement.setString(2, coffeeName);
            preparedStatement.setInt(3, supplierID);
            preparedStatement.setFloat(4, price);
            preparedStatement.setInt(5, sales);
            preparedStatement.setInt(6, total);

            rowsAffected= preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int saveWithAutoIncrementalID (String coffeeName, int supplierID, float price,
                                          int sales, int total){
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_COFFEE_WITH_AUTOINCREMENTAL_ID,
                                                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, coffeeName);
            preparedStatement.setInt(3, supplierID);
            preparedStatement.setFloat(4, price);
            preparedStatement.setInt(5, sales);
            preparedStatement.setInt(6, total);

            rowsAffected= preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                int auto_id = rs.getInt(1);
                System.out.println("The id of the new row is "+auto_id);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    


    
    public int delete(int id) {
        int rowsAffected=0;
        try (Connection con = db.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_COFFEE)) {
            preparedStatement.setInt(1, id);
            // executeUpdate method for INSERT, UPDATE and DELETE
            rowsAffected= preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(BasicCoffeeDAO.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rowsAffected;
    }

    // Private methods
    private void readRS(ResultSet rs) {
        try {
            while (rs.next()) {
                int prodId = rs.getInt("id_prod");
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUPP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(prodId + ", " + coffeeName + ", " + supplierID + ", " + price +
                        ", " + sales + ", " + total);


                // Reading the ResultSet with indexes
//                    while (rs.next()) {
//                        String coffeeName = rs.getString(1);
//                        int supplierID = rs.getInt(2);
//                        float PRICE = rs.getFloat(3);
//                        int SALES = rs.getInt(4);
//                        int total = rs.getInt(5);
//                        System.out.println(coffeeName + ", " + supplierID + ", "
//                                        + PRICE + ", " + SALES + ", " + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
