package CoffeeExampleSpringTemplate.dao;

import CoffeeExampleSpringTemplate.model.EuropeanCode;
import CoffeeExampleSpringTemplate.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product c = new Product();
        c.setId_prod(rs.getInt("id_prod"));
        c.setCountry(rs.getString("country"));
        EuropeanCode eu= new EuropeanCode(rs.getInt("firstdig"),rs.getInt("seconddig"), rs.getInt("thirddig") );
        c.setEu(eu);
        return c;
    }
}
