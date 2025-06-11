package dao;

import domain.modelo.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getAll();

    Usuario get(String username);

    boolean save(Usuario o);

    boolean modify(Usuario o, Usuario o2);

    boolean delete(String username);
}
