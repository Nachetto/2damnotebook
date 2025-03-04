package dao.repository.jdbc;

import common.configuration.Configuration;
import dao.repository.FactionsRepository;
import dao.model.Faction;
import dao.model.FactionsXML;
import dao.model.Weapon;
import dao.util.DBConnectionPool;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import domain.error.InternalServerException;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.sql.*;
import java.util.List;

@Log4j2
public class FactionsRepositoryJdbc implements FactionsRepository {
    private final Configuration config;
    private final DBConnectionPool pool;
    public FactionsRepositoryJdbc() {
        pool= new DBConnectionPool();
        config = Configuration.getInstance();
    }


    @Override
    public List<Faction> getAll() {
        return List.of();
    }

    @Override
    public Faction get(int id) {
        return null;
    }

    @Override
    public int update(Faction faction) {
        return 0;
    }

    @Override
    public boolean delete(Faction faction) {
        return false;
    }

    @Override
    public int save(Faction faction) {
        int result=-1;
        List<Weapon> weapons = faction.getWeapons();
        try (Connection conn = pool.getConnection();
             PreparedStatement addFactions = conn.prepareStatement("insert into faction (fname, contact, number_controlled_systems, date_last_purchase, planet) values (?,?,?,?,?)");
             PreparedStatement addWeapons = conn.prepareStatement("insert into weapons(wname, wprice) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement saveWeaponFactions = conn.prepareStatement("insert into weapons_factions( name_faction, id_weapon)  values (?,?)")
        ) {
            try {
                conn.setAutoCommit(false);
                addFactions.setString(1, faction.getName());
                addFactions.setString(2, faction.getContact());
                addFactions.setInt(3, faction.getNumberCS());
                addFactions.setDate(4, Date.valueOf(faction.getDateLastPurchase().toString()));
                addFactions.setString(5, faction.getPlanet());
                addFactions.executeUpdate();

                if (weapons != null && !weapons.isEmpty()) {
                    for (Weapon weapon : weapons) {
                        addWeapons.setString(1, weapon.getName());
                        addWeapons.setInt(2, weapon.getPrice());
                        addWeapons.executeUpdate();

                        try (ResultSet rs = addWeapons.getGeneratedKeys()) {
                            if (rs.next()) {
                                saveWeaponFactions.setString(1, faction.getName());
                                saveWeaponFactions.setInt(2, rs.getInt(1));
                                saveWeaponFactions.addBatch();
                            }
                        }
                    }
                    saveWeaponFactions.executeBatch();
                }
                conn.commit();

                result =1;
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                log.error(e.getMessage(), e);
                throw new InternalServerException("Foreign key error while adding fations with weapons");
            } catch (SQLException e) {
                conn.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    //XML
    public List<Faction> loadFromXML() {
        File file = new File(config.getPathVisitsXML());
        if (!file.exists()) {
            throw new InternalServerException("Error leyendo el archivo: " + file.getName());
        }
        try {
            // El contexto en el que le pasas el root element
            JAXBContext context = JAXBContext.newInstance(FactionsXML.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Desde la raíz, te metes en el elemento que quieres
            return ((FactionsXML) unmarshaller.unmarshal(file)).getFactions();
        } catch (JAXBException e) {
            throw new InternalServerException("Error while reading the xml, see more details:\n" +
                    e.getMessage());
        }
    }

    public static void main(String[] args) {
        FactionsRepositoryJdbc dao = new FactionsRepositoryJdbc();
        List<Faction> factions = dao.loadFromXML();
        factions.forEach(dao::save);
    }
}
