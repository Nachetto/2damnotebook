package usecases.usuarios;

import dao.impl.UsuariosDaoImpl;
import domain.errores.ClienteError;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class LoginUseCase {

    private final UsuariosDaoImpl dao;

    @Inject
    public LoginUseCase(UsuariosDaoImpl dao) {
        this.dao = dao;
    }

    public Single<Either<ClienteError, String>> login(String user, String password) {
        return dao.login(user, password);
    }
}
