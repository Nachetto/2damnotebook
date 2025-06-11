package CoffeeExampleSpringTemplate.dao;


import CoffeeExampleSpringTemplate.DBConnectionPool;
import CoffeeExampleSpringTemplate.model.Supplier;
import jakarta.inject.Inject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDAO {

	// SQL statements - Store into a file
	private static final String SELECT_suppliers_QUERY = "select * from suppliers";
    private static final String SELECT_supplier_QUERY = "select * from suppliers where supp_id=?";
    private static final String SELECT_numberSuppliers_QUERY = "select count(*) from suppliers";
    private static final String UPDATE_supplier_QUERY = "update suppliers set STREET = ?, COUNTRY=? where SUPP_ID = ?";
    private static final String DELETE_supplier_QUERY = "delete from suppliers where supp_id = ?";
    private static final String DELETE_coffee_QUERY = "delete from coffees where supp_id = ?";

    private final DBConnectionPool pool;

    @Inject
    public SupplierDAO(DBConnectionPool pool) {
        this.pool = pool;
    }
        public List<Supplier> getAllSuppliers() throws Exception {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Supplier> l= jtm.query(SELECT_suppliers_QUERY, BeanPropertyRowMapper.newInstance(Supplier.class));
             return l;
    }
        
        public Supplier getSupplier(int id) {

            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            //Exception if null
            //Supplier sup = jtm.queryForObject(SELECT_supplier_QUERY, BeanPropertyRowMapper.newInstance(Supplier.class),id);
            //return sup;
            List <Supplier> lsup = jtm.query(SELECT_supplier_QUERY, BeanPropertyRowMapper.newInstance(Supplier.class),id);
            return lsup.isEmpty() ? null : lsup.get(0);
  }
    public int getNumberOfSuppliers() {

        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        int  nsup = jtm.queryForObject(SELECT_numberSuppliers_QUERY, Integer.class);
        return nsup;
    }
        
        public int updateSupplier(Supplier s) {

            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            return jtm.update(UPDATE_supplier_QUERY, s.getStreet(), s.getCountry(), s.getSupp_id());
          }
        
        public int deleteSupplier(int id) {
            int res = -1;

            try {
              JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());

              res = jtm.update(DELETE_supplier_QUERY, id);
            } catch (DataIntegrityViolationException e) {
              if (e.getMessage().contains("IntegrityConstraintViolation")) {
                res = -2;
              }
            } catch (Exception ex) {
              Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return res;
          }

    public int deleteSupplierWithCoffees(int id) {
        int res = -1;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            res = jtm.update(DELETE_coffee_QUERY, id);
            res = jtm.update(DELETE_supplier_QUERY, id);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return res;
    }

        public int addSupplier(Supplier s) {

            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(pool.getDataSource()).withTableName("suppliers");
            
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("SUPP_ID", s.getSupp_id());
            parameters.put("STREET", s.getStreet());
            parameters.put("COUNTRY", s.getCountry());
             int res = jdbcInsert.execute(parameters);
            return res;
        }
        
         public Supplier addSupplierGK(Supplier s) {

            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(pool.getDataSource()).withTableName("suppliers")
                    .usingGeneratedKeyColumns("SUPP_ID");
            
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("STREET", s.getStreet());
            parameters.put("COUNTRY", s.getCountry());
            s.setSupp_id((int) jdbcInsert.executeAndReturnKey(parameters).longValue());
            return s;
  }
}
