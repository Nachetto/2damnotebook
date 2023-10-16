package dao;

import io.vavr.control.Either;
import model.MenuItem;

import java.util.List;

public interface MenuItemDAO {
    Either<String, List<MenuItem>> getAll();
}

