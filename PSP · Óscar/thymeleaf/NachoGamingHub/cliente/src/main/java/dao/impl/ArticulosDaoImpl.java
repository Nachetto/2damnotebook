package dao.impl;

import dao.ArticulosDao;
import dao.DaoGenerico;
import dao.retrofit.llamadas.ArticulosApi;
import domain.errores.ClienteError;
import domain.modelo.Articulo;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ArticulosDaoImpl extends DaoGenerico implements ArticulosDao {
    private final ArticulosApi articulosApi;

    @Inject
    public ArticulosDaoImpl(ArticulosApi articulosApi) {
        this.articulosApi = articulosApi;
    }

    @Override
    public Single<Either<ClienteError, List<Articulo>>> getAllArticulos() {
        return safeSingleApicall(articulosApi.getAllArticulos());
    }

    @Override
    public Single<Either<ClienteError, List<Articulo>>> getAllArticulosByJuego(String uuid) {
        return safeSingleApicall(articulosApi.getAllArticulosByJuego(uuid));
    }

    @Override
    public Single<Either<ClienteError, List<Articulo>>> getAllArticulosByUsuario(String uuid) {
        return safeSingleApicall(articulosApi.getAllArticulosByUsuario(uuid));
    }

    @Override
    public Single<Either<ClienteError, Articulo>> getArticulo(String uuid) {
        return safeSingleApicall(articulosApi.getArticulo(uuid));
    }

    @Override
    public Single<Either<ClienteError, Articulo>> addArticulo(Articulo articulo) {
        return safeSingleApicall(articulosApi.addArticulo(articulo));
    }

    @Override
    public Single<Either<ClienteError, Articulo>> updateArticulo(String uuid, Articulo articuloModificado) {
        return safeSingleApicall(articulosApi.updateArticulo(uuid, articuloModificado));
    }

    @Override
    public Single<Either<ClienteError, String>> deleteArticulo(String uuid) {
        return safeSingleVoidApicall(articulosApi.deleteArticulo(uuid));
    }
}