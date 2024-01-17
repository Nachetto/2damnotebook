package dao;

import domain.errores.ClienteError;
import domain.modelo.Articulo;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface ArticulosDao {
    Single<Either<ClienteError, List<Articulo>>> getAllArticulos();

    Single<Either<ClienteError, List<Articulo>>> getAllArticulosByJuego(String uuid);

    Single<Either<ClienteError, List<Articulo>>> getAllArticulosByUsuario(String uuid);

    Single<Either<ClienteError, Articulo>> getArticulo(String uuid);

    Single<Either<ClienteError, Articulo>> addArticulo(Articulo articulo);

    Single<Either<ClienteError, Articulo>> updateArticulo(String uuid, Articulo articuloModificado);

    Single<Either<ClienteError, String>> deleteArticulo(String uuid);
}
