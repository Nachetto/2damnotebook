package dao.util.jdbc;

import dao.model.Faction;
import org.springframework.stereotype.Component;
import domain.error.InternalServerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FactionMapper {
    public List<Faction> readRS(ResultSet rs) {
        List<Faction> factions = new ArrayList<>();
        try {
            while (rs.next()) factions.add(new Faction(
                    rs.getString("fname"),
                    rs.getString("contact"),
                    rs.getString("planet"),
                    rs.getInt("number_controlled_systems"),
                    rs.getDate("date_last_purchase").toLocalDate()
            ));
            return factions;
        } catch (SQLException e) {
            throw new InternalServerException("Error al mapear las fations desde la base de datos: "+e.getMessage());
        }
    }
}
