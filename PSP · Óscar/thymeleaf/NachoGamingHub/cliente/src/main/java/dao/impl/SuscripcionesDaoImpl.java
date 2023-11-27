package dao.impl;

import dao.DaoGenerico;
import dao.SuscripcionesDao;
import dao.retrofit.llamadas.SuscripcionesApi;
import domain.errores.ClienteError;
import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class SuscripcionesDaoImpl extends DaoGenerico implements SuscripcionesDao {
    private final SuscripcionesApi suscripcionesApi;

    @Inject
    public SuscripcionesDaoImpl(SuscripcionesApi suscripcionesApi) {
        this.suscripcionesApi = suscripcionesApi;
    }

    @Override
    public Single<Either<ClienteError, List<Suscripcion>>> getAllSuscripciones() {
        return safeSingleApicall(suscripcionesApi.getAllSuscripciones());
    }

    @Override
    public Single<Either<ClienteError, List<Suscripcion>>> getAllSuscripcionesByUsuario(String uuid) {
        return safeSingleApicall(suscripcionesApi.getAllSuscripcionesByUsuario(uuid));
    }

    @Override
    public Single<Either<ClienteError, Suscripcion>> getSuscripcion(String uuid) {
        return safeSingleApicall(suscripcionesApi.getSuscripcion(uuid));
    }

    @Override
    public Single<Either<ClienteError, Suscripcion>> addSuscripcion(Suscripcion suscripcion) {
        return safeSingleApicall(suscripcionesApi.addSuscripcion(suscripcion));
    }

    @Override
    public Single<Either<ClienteError, Suscripcion>> updateSuscripcion(String uuid, Suscripcion suscripcionModificada) {
        return safeSingleApicall(suscripcionesApi.updateSuscripcion(uuid, suscripcionModificada));
    }

    @Override
    public Single<Either<ClienteError, String>> deleteSuscripcion(String uuid) {
        return safeSingleVoidApicall(suscripcionesApi.deleteSuscripcion(uuid));
    }
}
