package usecases.usuarios;

import dao.impl.UsuariosDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class GetIdFromUserNameUseCase {

    private final UsuariosDaoImpl dao;
    @Inject
    public GetIdFromUserNameUseCase(UsuariosDaoImpl dao) {
        this.dao = dao;
    }

    public Single<Either<ClienteError, Usuario>> getUsuarioFromUserName(String userName) {
        return dao.getUsuarioFromUserName(userName);
    }
}
