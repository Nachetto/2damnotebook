package JDBCCoffeeExampleWithPool;

import JDBCCoffeeExample.utils.SQLQueries;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoffeeDAO {


    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private PreparedStatement pstmt2;
    private final DBConnectionPool pool;

    @Inject
    public CoffeeDAO(DBConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * Lists all coffees using Statement Class
     */
    public void getAll() {

        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_coffees_QUERY);
            //rs.absolute(2);
            //rs.previous();
            readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(CoffeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    /**
     * updateCoffeePrices method: Updates prices given a new percentage, with Resultset
     */
    public void updateCoffeePrices(float percentage) {
        stmt = null;
        rs = null;

        try (Connection con = pool.getConnection();
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);){

            rs = statement.executeQuery(SQLQueries.SELECT_coffees_QUERY);

            while (rs.next()) {
                float f = rs.getFloat("PRICE");
                // Updates ResultSet price column
                rs.updateFloat("PRICE", f * percentage);
                // Updates ResultSet row. Changes are saved in DB
                rs.updateRow();
            }
            // Sets the cursor before first row
            rs.beforeFirst();
            // Scrolls through the updated ResultSet
            System.out.println("\nAfter modifying the price:\n");
            readRS(rs);

        } catch (SQLException sqle) {
            Logger.getLogger(CoffeeDAO.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }


        /**
	 * insertNewRow method: Inserts a new row using ResultSet
	 */
	
	public void saveAndDeleteWithRS(int id, String coffeeName, int supplierID, float PRICE,
                                    int SALES, int total)  {
            stmt = null;
            try (Connection con = pool.getConnection();
                 Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_UPDATABLE);){

                rs = statement.executeQuery(SQLQueries.SELECT_coffees_QUERY);

                //No need to have the whole table data:
                //rs = statement.executeQuery("select * from coffees where COF_NAME= 'Espresso'");

                /*
                 * For inserting a row within the ResultSet, the cursor has to be situated
                 * on a special row named "insert row", where values can be assigned
                 */
                rs.moveToInsertRow();

                rs.updateInt(1,id);
                rs.updateString("COF_NAME", coffeeName);
                rs.updateInt("SUPP_ID", supplierID);
                rs.updateFloat("PRICE", PRICE);
                rs.updateInt("SALES", SALES);
                rs.updateInt("TOTAL", total);

                // Inserts ResultSet row. Changes are saved in DB 		
                rs.insertRow();

                // Scrolls through the updated ResultSet
                System.out.println("\nAfter inserting new row:\n");
                rs.beforeFirst();
                readRS((rs));

                rs.last();
                rs.deleteRow();

                System.out.println("\nAfter deleting last row:\n");
                rs.beforeFirst();
                readRS((rs));


            } catch (SQLException sqle) {
                Logger.getLogger(CoffeeDAO.class.getName()).log(Level.SEVERE, null, sqle);
            }
	}
        
        /**
	 * UpdateSales method: Updates coffee sales and total using a transaction
	 */
        public void updateSales(HashMap<String, Integer> sales)
	{
            pstmt = null;
            pstmt2 = null;

            try (Connection con = pool.getConnection();) {

                try (PreparedStatement pstmt = con.prepareStatement(SQLQueries.UPDATE_SALES_coffees);
                     PreparedStatement pstmt2 = con.prepareStatement(SQLQueries.UPDATE_TOTAL_COFFEE);) {

                    // Disables autocommit
                    con.setAutoCommit(false);

                    for (Map.Entry<String, Integer> e : sales.entrySet()) {
                        pstmt.setInt(1, e.getValue());
                        pstmt.setString(2, e.getKey());
                        pstmt.executeUpdate();
                        pstmt2.setInt(1, e.getValue());
                        pstmt2.setString(2, e.getKey());
                        pstmt2.executeUpdate();

                        // Commit when all movements have been done
                        con.commit();
                    }
                } catch (SQLException sqle) {
                    Logger.getLogger(CoffeeDAO.class.getName()).log(Level.SEVERE, null, sqle);
                    try {
                        con.rollback();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
