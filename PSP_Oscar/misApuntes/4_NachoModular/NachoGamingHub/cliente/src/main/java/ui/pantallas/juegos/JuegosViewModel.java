package ui.pantallas.juegos;

import domain.modelo.Juego;
import domain.modelo.Suscripcion;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import usecases.juegos.GetAllJuegosUseCase;
import usecases.suscripciones.GetAllSuscripcionesUseCase;
import usecases.suscripciones.UpdateSuscripcionesUseCase;
import usecases.usuarios.GetIdFromUserNameUseCase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class JuegosViewModel {
    private final ObjectProperty<JuegosState> state;
    private final GetAllJuegosUseCase juegosUseCase;
    private final GetAllSuscripcionesUseCase suscripcionesUseCase;
    private final GetIdFromUserNameUseCase getIdFromUserNameUseCase;
    private final UpdateSuscripcionesUseCase updateSuscripcionesUseCase;

    @Inject
    public JuegosViewModel(UpdateSuscripcionesUseCase updateSuscripcionesUseCase, GetIdFromUserNameUseCase getIdFromUserNameUseCase, GetAllJuegosUseCase juegosUseCase, GetAllSuscripcionesUseCase suscripcionesUseCase) {
        this.updateSuscripcionesUseCase = updateSuscripcionesUseCase;
        this.getIdFromUserNameUseCase = getIdFromUserNameUseCase;
        this.suscripcionesUseCase = suscripcionesUseCase;
        this.juegosUseCase = juegosUseCase;
        this.state = new SimpleObjectProperty<>(new JuegosState(null, null, null, null, null));
    }

    public void setIdUsuarioLogueado(String username) {
        getIdFromUserNameUseCase.getUsuarioFromUserName(username)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> state.setValue(new JuegosState(null, either.get().getUuid(), null, null, null)));
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> state.setValue(new JuegosState(either.getLeft(), null, null, null, null)));
                    }
                });
    }

    public ReadOnlyObjectProperty<JuegosState> getState() {
        return state;
    }

    public void cargarJuegos() {
        juegosUseCase.getAllJuegos()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> state.setValue(new JuegosState(null, state.get().getIdUsuarioLogueado(), null, null, either.get())));
                    } else if (either.isLeft()) {
                        Platform.runLater(() ->
                                state.setValue(new JuegosState(either.getLeft(), state.get().getIdUsuarioLogueado(), null, null, null)));

                    }
                });
    }

    public void cargarSuscripciones() {
        suscripcionesUseCase.getAllSuscripciones()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> state.setValue(new JuegosState(null, state.get().getIdUsuarioLogueado(), null, either.get(), state.get().getJuegos())));
                    } else if (either.isLeft()) {
                        Platform.runLater(() ->
                                state.setValue(new JuegosState(either.getLeft(), state.get().getIdUsuarioLogueado(), null, null, state.get().getJuegos())));

                    }
                });
    }

    public boolean verificarSuscripcion(Juego juegoSeleccionado) {
        if (state.get().getSuscripciones() != null) {
            for (Juego juego : state.get().getJuegos()) {
                if (juegoSeleccionado != null && juego.getUuid().equals(juegoSeleccionado.getUuid()) &&
                        state.get().getSuscripciones().stream().anyMatch(suscripcion -> suscripcion.getJuegoID().equals(juegoSeleccionado.getUuid()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void suscribirse(Juego selectedItem) {
        List<Suscripcion> suscripciones = state.get().getSuscripciones();
        suscripciones.add(new Suscripcion(UUID.randomUUID(), state.get().getIdUsuarioLogueado(), selectedItem.getUuid(), LocalDate.now()));
        state.setValue(new JuegosState(null, state.get().getIdUsuarioLogueado(), selectedItem, suscripciones, state.get().getJuegos()));

    }

    public void desuscribirse(Juego selectedItem) {
        List<Suscripcion> suscripciones = state.get().getSuscripciones();
        if (suscripciones.removeIf(suscripcion -> suscripcion.getUsuarioID().equals(state.get().getIdUsuarioLogueado()) && suscripcion.getJuegoID().equals(selectedItem.getUuid()))) {
            state.setValue(new JuegosState(null, state.get().getIdUsuarioLogueado(), selectedItem, suscripciones, state.get().getJuegos()));
        }
    }

    public void saveSubscripcions() {
        updateSuscripcionesUseCase.updateSuscripciones(state.get().getSuscripciones())
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() ->
                                state.setValue(new JuegosState(null, state.get().getIdUsuarioLogueado(), state.get().getJuegoSeleccionado(), state.get().getSuscripciones(), state.get().getJuegos())));
                    } else if (either.isLeft()) {
                        Platform.runLater(() ->
                                state.setValue(new JuegosState(either.getLeft(), state.get().getIdUsuarioLogueado(), state.get().getJuegoSeleccionado(), state.get().getSuscripciones(), state.get().getJuegos())));
                    }
                });
    }
}
