package dao;

import domain.errores.ClienteError;
import domain.modelo.Juego;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface JuegosDao {
    Single<Either<ClienteError, List<Juego>>> getAllJuegos();

    Single<Either<ClienteError, Juego>> getJuego(String id);

    Single<Either<ClienteError, Juego>> addJuego(Juego juego);

    Single<Either<ClienteError, Juego>> updateJuego(String id, Juego juegoModificado);

    Single<Either<ClienteError, String>> deleteJuego(String id);
}

