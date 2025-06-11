package usecases.juegos;

import dao.impl.JuegosDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Juego;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllJuegosUseCase {
    JuegosDaoImpl juegosDaoImpl;
    @Inject
    public GetAllJuegosUseCase(JuegosDaoImpl juegosDaoImpl) {
        this.juegosDaoImpl = juegosDaoImpl;
    }


    public Single<Either<ClienteError, List<Juego>>> getAllJuegos() {
        return juegosDaoImpl.getAllJuegos();
    }
}
