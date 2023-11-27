package dao;

import domain.modelo.Juego;

import java.util.List;
import java.util.UUID;

public interface JuegoDao {
    List<Juego> getAll();

    Juego get(String uuid);

    Juego save(Juego o);

    Juego modify(Juego o, Juego o2);

    boolean delete(UUID uuid);
}
