package service;

import dao.impl.JuegoDaoImpl;
import domain.modelo.Juego;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.util.List;
import java.util.UUID;

public class JuegoService {


    private JuegoDaoImpl dao;

    @Inject
    public JuegoService(JuegoDaoImpl dao) {
        this.dao = dao;
    }

    public List<Juego> getAll() throws BaseDatosCaidaException {
        return dao.getAll();
    }

    public Juego get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        return dao.get(uuid);
    }

    public Juego save(Juego o) throws BaseDatosCaidaException, OtraException {
        return dao.save(o);
    }

    public Juego modify(Juego o, Juego o2) throws BaseDatosCaidaException, OtraException {
        return dao.modify(o, o2);
    }

    public boolean delete(UUID o) throws OtraException, NotFoundException {
        return dao.delete(o);
    }
}
