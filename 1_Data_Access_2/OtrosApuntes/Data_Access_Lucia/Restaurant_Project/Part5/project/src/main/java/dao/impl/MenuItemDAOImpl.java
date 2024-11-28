package dao.impl;

import common.Constants;
import dao.MenuItemDAO;
import dao.common.DBConnection;
import dao.common.SQLConstants;
import jakarta.inject.Inject;
import model.MenuItem;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Log4j2
public class MenuItemDAOImpl implements MenuItemDAO {

    final DBConnection db;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public MenuItemDAOImpl(DBConnection db) {
        this.db = db;

        jdbcTemplate= new JdbcTemplate(db.getDataSource());
    }

    private final RowMapper<MenuItem> menuItemRowMapper = (rs, rowNum) -> new MenuItem(
            rs.getInt("menu_item_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price")
    );

    public Either<String, List<MenuItem>> getAll() {
        try {
            String sql = SQLConstants.SELECT_MENU_ITEMS_QUERY; // Asegúrate de que esta es tu consulta SQL correcta
            List<MenuItem> menuItems = jdbcTemplate.query(sql, menuItemRowMapper);
            return Either.right(menuItems);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.MENUITEMDBERROR + e.getMessage());
        }
    }

    public Either<String, MenuItem> get(int id) {
        try {
            String sql = SQLConstants.SELECT_MENU_ITEM_QUERY; // Asegúrate de que esta es tu consulta SQL correcta
            MenuItem menuItem = jdbcTemplate.queryForObject(sql, new Object[]{id}, menuItemRowMapper);
            return Either.right(menuItem);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constants.ERROROBTAININGMENUITEM + e.getMessage());
        }
    }

    @Override
    public int save(MenuItem menuItem) {
        try {
            String sql = SQLConstants.INSERT_MENU_ITEM_QUERY; // Asegúrate de que esta es tu consulta SQL correcta
            return jdbcTemplate.update(sql,
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getDescription(),
                    menuItem.getPrice());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int modify(MenuItem menuItem) {
        try {
            String sql = SQLConstants.UPDATE_MENU_ITEM_QUERY; // Asegúrate de que esta es tu consulta SQL correcta
            return jdbcTemplate.update(sql,
                    menuItem.getName(),
                    menuItem.getDescription(),
                    menuItem.getPrice(),
                    menuItem.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int delete(MenuItem menuItem) {
        try {
            String sql = SQLConstants.DELETE_MENU_ITEM_QUERY; // Asegúrate de que esta es tu consulta SQL correcta
            return jdbcTemplate.update(sql, menuItem.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
}
