package service;
import dao.impl.MenuItemDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.MenuItem;

import java.util.List;

public class MenuItemService {
    @Inject
    private MenuItemDAOImpl dao;

    public Either<String, List<MenuItem>> getAll() {
        return dao.getAll();
    }

    public Either<String, MenuItem> get(int id) {
        return dao.get(id);
    }

    public int save(MenuItem m) {
        return dao.save(m);
    }

    public int modify(MenuItem m) {
        return dao.modify(m);
    }

    public int delete(MenuItem m) {
        return dao.delete(m);
    }
}
