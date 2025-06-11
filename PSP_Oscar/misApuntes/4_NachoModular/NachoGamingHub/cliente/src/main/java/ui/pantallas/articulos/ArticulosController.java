package ui.pantallas.articulos;

import domain.modelo.Articulo;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class ArticulosController extends BasePantallaController {
    @FXML
    private TableView<Articulo> listaArticulos;
    @FXML
    private TableColumn<String, Articulo> titulo;
    @FXML
    private TableColumn<String, Articulo> contenido;
    @FXML
    private TableColumn<LocalDate, Articulo> fechaPublicacion;

    private final ArticulosViewModel viewmodel;


    @Inject
    public ArticulosController(ArticulosViewModel viewmodel) {
        this.viewmodel = viewmodel;
    }


    @Override
    public void principalCargado() {
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        contenido.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        fechaPublicacion.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacion"));
        viewmodel.cargarArticulos();
        observarState();
    }

    private void observarState() {
        viewmodel.getState().addListener((observable, oldValue, newValue) ->
            Platform.runLater(() -> {
                if (newValue.getArticulos() != null) {
                    listaArticulos.getItems().clear();
                    listaArticulos.getItems().addAll(newValue.getArticulos());
                }
            })

        );
    }

}