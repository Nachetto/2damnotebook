package dao;

import domain.errores.ClienteError;
import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface SuscripcionesDao {
    Single<Either<ClienteError, List<Suscripcion>>> getAllSuscripciones();

    Single<Either<ClienteError, List<Suscripcion>>> getAllSuscripcionesByUsuario(String uuid);

    Single<Either<ClienteError, Suscripcion>> getSuscripcion(String uuid);

    Single<Either<ClienteError, Suscripcion>> addSuscripcion(Suscripcion suscripcion);

    Single<Either<ClienteError, Suscripcion>> updateSuscripcion(String uuid, Suscripcion suscripcionModificada);

    Single<Either<ClienteError, String>> deleteSuscripcion(String uuid);
}
