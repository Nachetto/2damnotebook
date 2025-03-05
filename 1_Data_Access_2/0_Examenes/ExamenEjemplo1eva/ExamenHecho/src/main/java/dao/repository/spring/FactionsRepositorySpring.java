package dao.repository.spring;

import dao.model.Faction;
import dao.model.Weapon;
import dao.repository.FactionsRepository;
import dao.util.DBConnectionPool;
import dao.util.spring.FactionRowMapper;
import domain.error.InternalServerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class FactionsRepositorySpring implements FactionsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final FactionRowMapper factionRowMapper;
    private final DBConnectionPool dbConnectionPool;
    public FactionsRepositorySpring(DBConnectionPool dbConnectionPool, FactionRowMapper factionRowMapper) {
        this.dbConnectionPool = dbConnectionPool;
        this.factionRowMapper = factionRowMapper;
        jdbcTemplate = new JdbcTemplate(dbConnectionPool.getDataSource());
    }

    @Override
    public List<Faction> getAll() {
        try {
            return jdbcTemplate.query("SELECT * from faction", factionRowMapper);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage()+", cause:"+e);
        }
    }

    @Override
    public Faction get(String name) {
        try {
            String sql = "SELECT * FROM faction WHERE fname = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, factionRowMapper);
        } catch (Exception e) {
            log.error("Error getting faction", e);
            return null; // Retorna null si ocurre un error
        }
    }

    @Override @Transactional
    public int update(Faction faction) {
        try {
            String sql = "UPDATE faction SET contact = ?, planet = ?, number_controlled_systems = ?, date_last_purchase = ? WHERE fname = ?";
            return jdbcTemplate.update(sql, faction.getContact(), faction.getPlanet(), faction.getNumberCS(), Date.valueOf(faction.getDateLastPurchase().toString()), faction.getName());
        } catch (Exception e) {
            log.error("Error updating faction", e);
            return -1; // Retorna -1 si ocurre un error
        }
    }

    @Override
    @Transactional
    public boolean delete(Faction faction) {
        try {
            String sql = "DELETE FROM faction WHERE fname = ?";
            int rowsAffected = jdbcTemplate.update(sql, faction.getName());
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new InternalServerException("Error deleting faction: "+e.getMessage() + ", cause:" + e);
        }
    }

    @Transactional
    public boolean deleteFactionWithCosas(Faction faction) {
        try {
            String sqlDeleteWeaponsFactions = "DELETE FROM weapons_factions WHERE name_faction = ?";
            String sqlDeleteWeapons = "DELETE FROM weapons WHERE id IN (SELECT id_weapon FROM weapons_factions WHERE name_faction = ?)";
            String sqlDeleteFaction = "DELETE FROM faction WHERE fname = ?";

            // Delete from weapons_factions
            jdbcTemplate.update(sqlDeleteWeaponsFactions, faction.getName());

            // Delete from weapons
            jdbcTemplate.update(sqlDeleteWeapons, faction.getName());

            // Delete from faction
            int rowsAffected = jdbcTemplate.update(sqlDeleteFaction, faction.getName());

            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error deleting faction with weapons", e);
            throw new InternalServerException(e.getMessage() + ", cause:" + e);
        }
    }

    @Override
    public int save(Faction faction) {
        try {
            String sql = "INSERT INTO faction(fname,contact,planet,number_controlled_systems,date_last_purchase) VALUES (?,?,?,?,?)";

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, faction.getName());
                ps.setString(2, faction.getContact());
                ps.setString(3, faction.getPlanet());
                ps.setInt(4, faction.getNumberCS());
                ps.setDate(5, Date.valueOf(faction.getDateLastPurchase().toString()));
                return ps;
            });

            return 1;
        } catch (Exception e) {
            log.error("Error saving faction", e);
            return -1; // Retorna -1 si ocurre un error
        }
    }

    @Transactional
    public int saveFactionWithCosas(Faction faction){
        int result = -1;
        List<Weapon> weapons = faction.getWeapons();
        try {
            String sqlFaction = "INSERT INTO faction(fname, contact, planet, number_controlled_systems, date_last_purchase) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sqlFaction, new String[]{"id"});//aqui va a fallar porque no tiene id, es solo un ejemplo
                ps.setString(1, faction.getName());
                ps.setString(2, faction.getContact());
                ps.setString(3, faction.getPlanet());
                ps.setInt(4, faction.getNumberCS());
                ps.setDate(5, Date.valueOf(faction.getDateLastPurchase().toString()));
                return ps;
            }, keyHolder);

            int fname = keyHolder.getKey().intValue();

            if (weapons != null && !weapons.isEmpty()) {
                String sqlWeapon = "INSERT INTO weapons(wname, wprice) VALUES (?, ?)";
                String sqlWeaponFaction = "INSERT INTO weapons_factions(name_faction, id_weapon) VALUES (?, ?)";

                for (Weapon weapon : weapons) {
                    KeyHolder weaponKeyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(sqlWeapon, new String[]{"id"});
                        ps.setString(1, weapon.getName());
                        ps.setInt(2, weapon.getPrice());
                        return ps;
                    }, weaponKeyHolder);

                    int weaponId = weaponKeyHolder.getKey().intValue();
                    jdbcTemplate.update(sqlWeaponFaction, faction.getName(), weaponId);
                }
            }

            result = fname;
        } catch (Exception e) {
            log.error("Error saving faction with weapons", e);
            throw new InternalServerException(e.getMessage() + ", cause:" + e);
        }
        return result;
    }


    public static void main(String[] args) {
        DBConnectionPool dbConnectionPool = new DBConnectionPool();
        FactionRowMapper factionRowMapper = new FactionRowMapper();
        FactionsRepositorySpring dao = new FactionsRepositorySpring(dbConnectionPool,factionRowMapper);
        List<Faction> factions = dao.getAll();
        factions.forEach(System.out::println);
    }
}

