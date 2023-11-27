package dao;

import domain.errores.ClienteError;
import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface UsuariosDao {
    public Single<Either<ClienteError, List<Usuario>>> getUsuario();
    public Single<Either<ClienteError, Usuario>> getUsuario(String id);
    public Single<Either<ClienteError, Usuario>> postUsuario(Usuario usuario);
    public Single<Either<ClienteError, Usuario>> putUsuario(Usuario usuario);
    public Single<Either<ClienteError, Usuario>> deleteUsuario(String id);
}