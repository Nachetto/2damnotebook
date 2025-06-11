package CoffeeExampleSpringTemplate.dao;

import CoffeeExampleSpringTemplate.DBConnectionPool;
import CoffeeExampleSpringTemplate.model.EuropeanCode;
import CoffeeExampleSpringTemplate.model.Product;
import jakarta.inject.Inject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProductDAO {

    // SQL statements - Store into a file
    private static final String SELECT_product_QUERY = "select * from products";
    private static final String SELECT_productsConEUCode_QUERY = "select * from products inner join euCodes on products.firstdig = euCodes.firstdig";

    private final DBConnectionPool pool;

    @Inject
    public ProductDAO(DBConnectionPool pool) {
        this.pool = pool;
    }

    public List<Product> getAllProducts() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        return jtm.query(SELECT_product_QUERY, BeanPropertyRowMapper.newInstance(Product.class));
    }

    public List<Product> getAllProductsWithEUCode() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List l = jtm.query(SELECT_productsConEUCode_QUERY, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

                Product c = new Product();
                c.setId_prod(rs.getInt("id_prod"));
                c.setCountry(rs.getString("country"));
                EuropeanCode eu = new EuropeanCode(rs.getInt("firstdig"), rs.getInt("seconddig"), rs.getInt("thirddig"));
                c.setEu(eu);
                return c;
            }
        });
        return l;
    }

    public List<Product> getAllProductsWithEUCodeUsingRowMapperSingleClass() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        List l = jtm.query(SELECT_productsConEUCode_QUERY, new ProductMapper());
        return l;
    }

}