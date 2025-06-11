package dao;

import domain.modelo.Suscripcion;

import java.util.List;

public interface SuscripcionDao {
    List<Suscripcion> getAll();
    Suscripcion get(String uuid);
    Suscripcion save(Suscripcion o);
    Suscripcion modify(Suscripcion o, Suscripcion o2);
    boolean delete(String uuid);
}
