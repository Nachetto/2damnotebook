package CoffeeExampleSpringTemplate.dao;

import CoffeeExampleSpringTemplate.DBConnectionPool;
import CoffeeExampleSpringTemplate.model.Coffee;
import CoffeeExampleSpringTemplate.model.EuropeanCode;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CoffeeDAO {

	// SQL statements - Store into a file
	private static final String SELECT_coffees_QUERY = "select * from coffees inner join products on coffees.id_prod = products.id_prod";
	private static final String SELECT_coffeesConEUCode_QUERY = "select * from coffees inner join products on coffees.id_prod = products.id_prod inner join eucodes on products.firstdig = eucodes.firstdig";

    private final DBConnectionPool pool;

    @Inject
    public CoffeeDAO(DBConnectionPool pool) {
        this.pool = pool;
    }

        public List<Coffee> getAllCoffees() {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List l= jtm.query(SELECT_coffees_QUERY, BeanPropertyRowMapper.newInstance(Coffee.class));
             return l;
    }
        
        public List<Coffee> getAllCoffeesWithEUCode() throws Exception {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List l= jtm.query(SELECT_coffeesConEUCode_QUERY, new RowMapper<Coffee>() {
                @Override
                public Coffee mapRow(ResultSet rs, int rowNum) throws SQLException {
                    
                    Coffee c = new Coffee();
                    c.setId_prod(rs.getInt("id_prod"));
                    c.setCof_name(rs.getString("cof_name"));
                    c.setPrice(rs.getInt("price"));
                    c.setSales(rs.getInt("sales"));
                    c.setSupp_id(rs.getInt("supp_id"));
                    c.setCountry(rs.getString("country"));
                    EuropeanCode eu= new EuropeanCode(rs.getInt("firstdig"),rs.getInt("seconddig"), rs.getInt("thirddig") );
                    c.setEu(eu);                 
                   return c;
                }
            });
             return l;

        }
}