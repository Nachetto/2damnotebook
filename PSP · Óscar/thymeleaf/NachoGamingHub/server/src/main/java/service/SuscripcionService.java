package service;

import dao.impl.SuscripcionDaoImpl;
import domain.modelo.Suscripcion;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.util.List;

public class SuscripcionService {
    SuscripcionDaoImpl dao;
    @Inject
    SuscripcionService(SuscripcionDaoImpl dao){
        this.dao = dao;
    }

    public List<Suscripcion> getAll() throws BaseDatosCaidaException {
        return dao.getAll();
    }

    public List<Suscripcion> getAll(String uuid) throws BaseDatosCaidaException, OtraException {
        return dao.getAll(uuid);
    }

    public Suscripcion get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        return dao.get(uuid);
    }

    public Suscripcion save(Suscripcion s) throws BaseDatosCaidaException, OtraException {
        return dao.save(s);
    }

    public Suscripcion modify(Suscripcion initialSubscription, Suscripcion modifiedSubscription) throws BaseDatosCaidaException, OtraException {
        return dao.modify(initialSubscription, modifiedSubscription);
    }

    public boolean delete(String uuid) {
        return dao.delete(uuid);
    }
}
