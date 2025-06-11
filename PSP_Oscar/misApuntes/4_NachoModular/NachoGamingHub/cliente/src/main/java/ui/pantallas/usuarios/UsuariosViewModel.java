package ui.pantallas.usuarios;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import usecases.usuarios.GetAllUsuariosUseCase;

public class UsuariosViewModel {
    private final ObjectProperty<UsuariosState> state;

    private final GetAllUsuariosUseCase getAllUsuariosUseCase;
    @Inject
    public UsuariosViewModel(GetAllUsuariosUseCase getAllUsuariosUseCase) {
        this.getAllUsuariosUseCase = getAllUsuariosUseCase;
        this.state = new SimpleObjectProperty<>(new UsuariosState(null,null, null));
    }


    public void cargarArticulos() {
        getAllUsuariosUseCase.getAllUsuarios()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() -> state.setValue(new UsuariosState(null,null,either.get())));
                    } else if (either.isLeft()) {
                        Platform.runLater(() -> state.setValue(new UsuariosState(either.getLeft(),null,null)));
                    }
                });
    }

    public ReadOnlyObjectProperty<UsuariosState> getState() {
        return state;
    }
}
