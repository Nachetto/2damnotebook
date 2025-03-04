package dao.util.spring;

import dao.model.Faction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class FactionRowMapper implements RowMapper<Faction> {
    @Override
    public Faction mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Faction(
                rs.getString("fname"),
                rs.getString("contact"),
                rs.getString("planet"),
                rs.getInt("number_controlled_systems"),
                rs.getDate("date_last_purchase").toLocalDate()
        );

    }
}
