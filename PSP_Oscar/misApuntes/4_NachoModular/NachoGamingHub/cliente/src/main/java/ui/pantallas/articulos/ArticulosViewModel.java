package ui.pantallas.articulos;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import usecases.articulos.GetAllArticulosUseCase;

public class ArticulosViewModel {
    private final ObjectProperty<ArticulosState> state;

    private final GetAllArticulosUseCase articulosUseCase;

    @Inject
    public ArticulosViewModel(GetAllArticulosUseCase articulosUseCase) {
        this.articulosUseCase = articulosUseCase;
        this.state = new SimpleObjectProperty<>(new ArticulosState(null, null, null));
    }

    public void cargarArticulos() {
        articulosUseCase.getAllArticulos()
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        Platform.runLater(() ->
                                state.setValue(new ArticulosState(null, null, either.get())));
                    } else if (either.isLeft()) {
                        Platform.runLater(() ->
                                state.setValue(new ArticulosState(either.getLeft(), null, null)));
                    }
                });
    }

    public ReadOnlyObjectProperty<ArticulosState> getState() {
        return state;
    }
}
