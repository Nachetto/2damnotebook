package usecases.suscripciones;

import dao.impl.SuscripcionesDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllSuscripcionesUseCase {
    SuscripcionesDaoImpl suscripcionesDaoImpl;
    @Inject
    public GetAllSuscripcionesUseCase(SuscripcionesDaoImpl suscripcionesDaoImpl) {
        this.suscripcionesDaoImpl = suscripcionesDaoImpl;
    }

    public Single<Either<ClienteError, List<Suscripcion>>> getAllSuscripciones() {
        return suscripcionesDaoImpl.getAllSuscripciones();
    }
}
