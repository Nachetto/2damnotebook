package service;

import common.RestaurantError;
import dao.MenuItemDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.MenuItem;

import java.util.List;

public class MenuItemServices {
    private final MenuItemDAO menuItemDAO;

    @Inject
    public MenuItemServices(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }

    public Either<RestaurantError,List<MenuItem>> getAll() {
        return menuItemDAO.getAll();
    }

    public Either<RestaurantError,MenuItem> get(int id) {
        return menuItemDAO.get(id);
    }

    public Either<RestaurantError,Integer> add(MenuItem menuItem) {
        return menuItemDAO.add(menuItem);
    }

    public Either<RestaurantError,Integer> update(MenuItem menuItem) {
        return menuItemDAO.update(menuItem);
    }

    public Either<RestaurantError,Integer> delete(MenuItem menuItem) {
        return menuItemDAO.delete(menuItem);
    }
}
