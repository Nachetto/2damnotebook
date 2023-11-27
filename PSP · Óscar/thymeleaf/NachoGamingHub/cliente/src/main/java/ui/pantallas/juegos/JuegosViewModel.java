package ui.pantallas.juegos;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import usecases.juegos.GetAllJuegosUseCase;
import usecases.suscripciones.GetAllSuscripcionesUseCase;

public class JuegosViewModel {
    private final ObjectProperty<JuegosState> state;
    private final GetAllJuegosUseCase juegosUseCase;
    private final GetAllSuscripcionesUseCase suscripcionesUseCase;

    @Inject
    public JuegosViewModel(GetAllJuegosUseCase juegosUseCase, GetAllSuscripcionesUseCase suscripcionesUseCase) {
        this.suscripcionesUseCase = suscripcionesUseCase;
        this.juegosUseCase = juegosUseCase;
        this.state = new SimpleObjectProperty<>(new JuegosState(\false, null, null, null));
    }

    public ReadOnlyObjectProperty<JuegosState> getState() {
        return state;
    }

    public void cargarJuegos() {
        //llamar al usecase de cargar juegos y cambiar el state con los juegos
        juegosUseCase.getAllJuegos()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> {
                            state.get().setJuegos(either.get());
                        });
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> {
                            state.get().setMensaje(either.getLeft().getMensaje());
                        });
                    }
                });
    }

    public void cargarSuscripciones() {
        //llamar al usecase de cargar suscripciones y cambiar el state con las suscripciones del usuario logueado
        suscripcionesUseCase.getAllSuscripciones()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> {
                            state.get().setSuscripciones(either.get());
                        });
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> {
                            state.get().setMensaje(either.getLeft().getMensaje());
                        });
                    }
                });
    }
}
