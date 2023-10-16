package dao;

import io.vavr.control.Either;
import model.MenuItem;

import java.util.List;

public interface MenuItemDAO {
    Either<String, List<MenuItem>> getAll();
    int save(MenuItem m);

    int modify(MenuItem m);

    int delete(MenuItem m);
}

