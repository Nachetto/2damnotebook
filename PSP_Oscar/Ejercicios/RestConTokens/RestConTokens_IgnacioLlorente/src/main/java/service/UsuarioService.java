package service;

import dao.impl.HasheoConstrasenas;
import dao.impl.UsuarioDaoImpl;
import domain.modelo.Usuario;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

public class UsuarioService {
    private final UsuarioDaoImpl dao;
    @Inject
    public UsuarioService(UsuarioDaoImpl dao){
        this.dao = dao;
    }

    public List<Usuario> getAll() {
        return dao.getAll();
    }

    public Usuario get(String username) {
        return dao.get(username);
    }

    public boolean save(String email, String password, String token) {
        //cifrar contraseña y subirla a database creando el usuario con todos los datos
        HasheoConstrasenas hasheoConstrasenas = new HasheoConstrasenas();
        String passwordHasheada = hasheoConstrasenas.hashPassword(password);

        Usuario o = new Usuario(email, passwordHasheada, false, LocalDate.now(), token);
        return dao.save(o);
    }

    public String generateActivationCode() {
        return dao.generateActivationCode();
    }

    public boolean verifyCode(String c) {
        return dao.verifyCode(c);
    }

    public boolean modify(Usuario o, Usuario o2) {
        return dao.modify(o, o2);
    }

    public boolean delete(String username) {
        return dao.delete(username);
    }
}
