package dao.repository.spring;

import dao.model.Faction;
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
            return jdbcTemplate.query("SELECT * from factions", factionRowMapper);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage()+", cause:"+e);
        }
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
        try {
            String sql = "INSERT INTO faction(fname,contact,planet,number_controlled_systems,date_last_purchase) VALUES (?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"patient_id"});
                ps.setString(1, faction.getName());
                ps.setString(2, faction.getContact());
                ps.setString(3, faction.getPlanet());
                ps.setInt(4, faction.getNumberCS());
                ps.setDate(5, Date.valueOf(faction.getDateLastPurchase().toString()));
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            log.error("Error saving faction", e);
            return -1; // Retorna -1 si ocurre un error
        }
    }


    public static void main(String[] args) {
        DBConnectionPool dbConnectionPool = new DBConnectionPool();
        FactionRowMapper factionRowMapper = new FactionRowMapper();
        FactionsRepositorySpring dao = new FactionsRepositorySpring(dbConnectionPool,factionRowMapper);
        List<Faction> factions = dao.getAll();
        factions.forEach(System.out::println);
    }
}

