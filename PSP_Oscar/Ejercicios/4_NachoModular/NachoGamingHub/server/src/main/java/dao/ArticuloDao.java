package dao;

import domain.modelo.Articulo;

import java.util.List;

public interface ArticuloDao {
    List<Articulo> getAll();
    List<Articulo> getAllByJuego(String uuid);
    List<Articulo> getAllByUsuario(String uuid);
    Articulo get(String uuid);
    Articulo save(Articulo o);
    Articulo modify(Articulo o, Articulo o2);
    boolean delete(String uuid);

}
