package dao;

import domain.modelo.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioDao {
    List<Usuario> getAll();

    Usuario get(String uuid);

    Usuario save(Usuario o);

    Usuario modify(Usuario o, Usuario o2);

    boolean delete(UUID uuid);
}
