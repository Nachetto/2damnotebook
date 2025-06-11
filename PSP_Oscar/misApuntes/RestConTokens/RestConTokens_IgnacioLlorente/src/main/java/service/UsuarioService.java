package service;

import dao.impl.HasheoConstrasenas;
import dao.impl.UsuarioDaoImpl;
import domain.modelo.Usuario;
import jakarta.inject.Inject;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

        Usuario o = new Usuario(email, passwordHasheada, false, LocalDateTime.now(), token);
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

    public boolean isActivated(String email) {
        return dao.isActivated(email);
    }

    public boolean login(String email, String password) {
        HasheoConstrasenas hasheoConstrasenas = new HasheoConstrasenas();
        String passwordHasheada = hasheoConstrasenas.hashPassword(password);
        return dao.login(email, passwordHasheada);
    }

    public String generateToken(String email, String tipo) {
        //verificar que el usuario está activado
        if (isActivated(email)) {
            //generar token
            return dao.generateToken(tipo,
                    email.split("@")[0]);
        }
        return null;
    }
    public String generateToken(String email, String tipo, String rolExtra) {
        //verificar que el usuario está activado
        if (isActivated(email)) {
            //generar token
            return dao.generateToken(tipo,
                    email.split("@")[0], rolExtra);
        }
        return null;
    }

    public SecretKey getSecretKey() throws NoSuchAlgorithmException {
        return dao.getSecretKey();
    }

    public String getUsernameFromEmail(String email) {
        return dao.getUsernameFromEmail(email);
    }
}
