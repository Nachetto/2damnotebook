package service;

import dao.impl.ArticuloDaoImpl;
import domain.modelo.Articulo;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.util.List;

public class ArticuloService {
    ArticuloDaoImpl dao;
    @Inject
    ArticuloService(ArticuloDaoImpl dao){
        this.dao = dao;
    }

    public List<Articulo> getAll() throws BaseDatosCaidaException {
        return dao.getAll();
    }

    public List<Articulo> getAllByJuego(String uuid) throws BaseDatosCaidaException, OtraException {
        return dao.getAllByJuego(uuid);
    }

    public List<Articulo> getAllByUsuario(String uuid) throws BaseDatosCaidaException, OtraException {
        return dao.getAllByUsuario(uuid);
    }

    public Articulo get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        return dao.get(uuid);
    }

    public Articulo save(Articulo o) throws BaseDatosCaidaException, OtraException {
        return dao.save(o);
    }

    public Articulo modify(Articulo initialArticulo, Articulo modifiedArticulo) throws BaseDatosCaidaException, OtraException {
        return dao.modify(initialArticulo, modifiedArticulo);
    }

    public boolean delete(String uuid) throws OtraException, NotFoundException {
        return dao.delete(uuid);
    }
}
