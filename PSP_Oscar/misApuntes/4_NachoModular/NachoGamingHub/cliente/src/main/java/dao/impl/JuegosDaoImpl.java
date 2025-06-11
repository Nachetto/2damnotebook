package dao.impl;

import dao.DaoGenerico;
import dao.JuegosDao;
import dao.retrofit.llamadas.JuegosApi;
import domain.errores.ClienteError;
import domain.modelo.Juego;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class JuegosDaoImpl extends DaoGenerico implements JuegosDao {
    private final JuegosApi juegosApi;

    @Inject
    public JuegosDaoImpl(JuegosApi juegosApi) {
        this.juegosApi = juegosApi;
    }

    @Override
    public Single<Either<ClienteError, List<Juego>>> getAllJuegos() {
        return safeSingleApicall(juegosApi.getAllJuegos());
    }

    @Override
    public Single<Either<ClienteError, Juego>> getJuego(String id) {
        return safeSingleApicall(juegosApi.getJuego(id));
    }

    @Override
    public Single<Either<ClienteError, Juego>> addJuego(Juego juego) {
        return safeSingleApicall(juegosApi.addJuego(juego));
    }

    @Override
    public Single<Either<ClienteError, Juego>> updateJuego(String id, Juego juegoModificado) {
        return safeSingleApicall(juegosApi.updateJuego(id, juegoModificado));
    }

    @Override
    public Single<Either<ClienteError, String>> deleteJuego(String id) {
        return safeSingleVoidApicall(juegosApi.deleteJuego(id));
    }
}

