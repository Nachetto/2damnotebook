package dao;

import domain.errores.ClienteError;
import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface UsuariosDao {
    Single<Either<ClienteError, List<Usuario>>> getUsuarios();
    Single<Either<ClienteError, Usuario>> getUsuario(String id);
    Single<Either<ClienteError, Usuario>> postUsuario(Usuario usuario);
    Single<Either<ClienteError, Usuario>> putUsuario(Usuario usuario);
    Single<Either<ClienteError, String>> deleteUsuario(String id);
    Single<Either<ClienteError, String>> login(String user, String password);
    Single<Either<ClienteError, String>> register(Usuario u);
}