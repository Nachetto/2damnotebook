package com.hospitalcrud.dao.mappers.spring_mappers;

import com.hospitalcrud.dao.model.Payment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapSpringPayments implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Payment(
                rs.getInt(2),
                (int)rs.getFloat(1));
    }
}
