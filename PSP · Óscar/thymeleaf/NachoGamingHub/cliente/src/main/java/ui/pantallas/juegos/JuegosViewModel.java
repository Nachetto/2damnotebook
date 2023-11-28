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

import java.util.List;

public class JuegosViewModel {
    private final ObjectProperty<JuegosState> state;
    private int idUsuarioLogueado;
    private final GetAllJuegosUseCase juegosUseCase;
    private final GetAllSuscripcionesUseCase suscripcionesUseCase;

    @Inject
    public JuegosViewModel(GetAllJuegosUseCase juegosUseCase, GetAllSuscripcionesUseCase suscripcionesUseCase, int idUsuarioLogueado) {
        this.suscripcionesUseCase = suscripcionesUseCase;
        this.juegosUseCase = juegosUseCase;
        this.idUsuarioLogueado = idUsuarioLogueado;
        //ClienteError error, Juego juegoSeleccionado, List<Suscripcion> suscripciones, List<Juego> juegos
        this.state = new SimpleObjectProperty<>(new JuegosState(null, null, null, null));
    }

    public void setIdUsuarioLogueado(String username) {

        this.idUsuarioLogueado = idUsuarioLogueado;
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
                            //new JuegosState(null, null, null, null)
                            state.setValue(new JuegosState(null, null, null, either.get()));
                        });
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> {
                            state.setValue(new JuegosState(either.getLeft(),null, null, null));
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
                            List<Suscripcion> suscripciones = either.get()
                                    .stream()
                                    .filter(suscripcion -> suscripcion
                                            .getUsuarioID()
                                            .equals(state.get().getUsuario().getUuid())).toList();
                            state.setValue(new JuegosState(null, null, either.get(), state.get().getJuegos()));
                        });
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> {
                            state.setValue(new JuegosState(either.getLeft(),null, null, state.get().getJuegos()));
                        });
                    }
                });
    }

    public void seleccionarJuego(Juego selectedItem) {
        state.setValue(new JuegosState(null, selectedItem, state.get().getSuscripciones(), state.get().getJuegos()));
    }

    public boolean verificarSuscripcion() {
        //verificar si el juego seleccionado esta en la lista de suscripciones del usuario logueado
        if (state.get().getSuscripciones() != null) {
            for (Juego juego : state.get().getJuegos()) {
                if (juego.getUuid().equals(state.get().getJuegoSeleccionado().getUuid())) {
                    return true;
                }
            }
        }
        return false;
    }
}
