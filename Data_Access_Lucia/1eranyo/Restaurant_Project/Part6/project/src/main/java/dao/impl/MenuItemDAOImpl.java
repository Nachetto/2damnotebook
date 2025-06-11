package dao.impl;

import common.Constants;
import dao.MenuItemDAO;
import dao.common.DBConnection;
import dao.common.SQLConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;

@Log4j2
public class MenuItemDAOImpl implements MenuItemDAO {

    private final DBConnection db;

    @Inject
    public MenuItemDAOImpl(DBConnection db) {
        this.db = db;
    }

    @PreDestroy
    public void closePool() {
        // Cerrar el pool de conexiones
        try {
            db.close();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public Either<String, List<MenuItem>> getAll() {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_MENU_ITEMS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<MenuItem> menuItems = new ArrayList<>();
            while (rs.next()) {
                MenuItem menuItem = new MenuItem(
                        rs.getInt("menu_item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                menuItems.add(menuItem);
            }
            return Either.right(menuItems);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.MENUITEMDBERROR + e.getMessage());
        }
    }

    public Either<String, MenuItem> get(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.SELECT_MENU_ITEM_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                MenuItem menuItem = new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                return Either.right(menuItem);
            }
            return Either.left(Constants.IDNOTFOUND + id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.ERROROBTAININGMENUITEM + e.getMessage());
        }
    }

    @Override
    public int save(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.INSERT_MENU_ITEM_QUERY)) {
            preparedStatement.setInt(1, menuItem.getId());
            preparedStatement.setString(2, menuItem.getName());
            preparedStatement.setString(3, menuItem.getDescription());
            preparedStatement.setDouble(4, menuItem.getPrice());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.UPDATE_MENU_ITEM_QUERY)) {
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getDescription());
            preparedStatement.setDouble(3, menuItem.getPrice());
            preparedStatement.setInt(4, menuItem.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public int delete(MenuItem menuItem) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLConstants.DELETE_MENU_ITEM_QUERY)) {
            preparedStatement.setInt(1, menuItem.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
