package usecases.usuarios;

import dao.impl.UsuariosDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Usuario;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllUsuariosUseCase {
    private final UsuariosDaoImpl dao;
    @Inject
    public GetAllUsuariosUseCase(UsuariosDaoImpl dao) {
        this.dao = dao;
    }

    public Single<Either<ClienteError, List<Usuario>>> getAllUsuarios() {
        return dao.getUsuarios();
    }
}
