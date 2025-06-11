package usecases.suscripciones;

import dao.impl.SuscripcionesDaoImpl;
import domain.errores.ClienteError;
import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class UpdateSuscripcionesUseCase {

    SuscripcionesDaoImpl suscripcionesDaoImpl;
    @Inject
    public UpdateSuscripcionesUseCase(SuscripcionesDaoImpl suscripcionesDaoImpl) {
        this.suscripcionesDaoImpl = suscripcionesDaoImpl;
    }

    public Single<Either<ClienteError, List<Suscripcion>>> updateSuscripciones(List<Suscripcion> suscripcionModificada) {
        return suscripcionesDaoImpl.updateSuscripciones(suscripcionModificada);
    }
}
