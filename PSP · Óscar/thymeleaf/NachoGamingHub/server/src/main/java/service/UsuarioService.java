package service;

import dao.impl.UsuarioDaoImpl;
import domain.modelo.Usuario;
import jakarta.inject.Inject;
import model.errores.BaseDatosCaidaException;
import model.errores.NotFoundException;
import model.errores.OtraException;

import java.util.List;
import java.util.UUID;

public class UsuarioService {
    UsuarioDaoImpl dao;
    @Inject
    UsuarioService(UsuarioDaoImpl dao){
        this.dao = dao;
    }

    public List<Usuario> getAll() throws BaseDatosCaidaException, NotFoundException {
        return dao.getAll();
    }

    public Usuario get(String uuid) throws BaseDatosCaidaException, NotFoundException, OtraException {
        return dao.get(uuid);
    }

    public Usuario save(Usuario c) throws BaseDatosCaidaException, OtraException {
        return dao.save(c);
    }

    public Usuario modify(Usuario initialcustomer, Usuario modifiedcustomer) throws BaseDatosCaidaException, OtraException {
        return dao.modify(initialcustomer, modifiedcustomer);
    }

    public boolean delete(UUID c) throws OtraException, NotFoundException {
        return dao.delete(c);
    }

    public boolean authenticate(String user, String password) {
        return dao.authenticate(user, password);
    }
}
