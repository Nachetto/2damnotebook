package dao.impl;

import dao.UsuarioDao;
import domain.modelo.Usuario;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {
    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("usuario1", "contraseña1", false, LocalDate.now(), "codigo1"));
        usuarios.add(new Usuario("usuario2", "contraseña2", false, LocalDate.now(), "codigo2"));
        usuarios.add(new Usuario("usuario3", "contraseña3", false, LocalDate.now(), "codigo3"));
        usuarios.add(new Usuario("usuario4", "contraseña4", false, LocalDate.now(), "codigo4"));
        usuarios.add(new Usuario("usuario5", "contraseña5", false, LocalDate.now(), "codigo5"));
    }

    @Override
    public List<Usuario> getAll() {
        return usuarios;
    }

    @Override
    public Usuario get(String username) {
        return usuarios.stream().filter(usuario -> usuario.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public boolean save(Usuario o) {
        return usuarios.add(o);
    }

    public String generateActivationCode() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    public boolean verifyCode(String c) {
        //buscar el usuario con el codigo de activacion y activarlo
        Usuario u = usuarios.stream()
                .filter(usuario -> usuario.getActivationCode().equals(c))
                .findFirst().orElse(null);
        if (u != null) {
            u.setActivated(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean modify(Usuario o, Usuario o2) {
        return true;
    }

    @Override
    public boolean delete(String username) {
        return false;
    }
}
