package ui.pantallas.juegos;

import domain.modelo.Juego;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class JuegosController extends BasePantallaController {
    JuegosViewModel viewmodel;
    @FXML
    private Button butonSuscribe;
    @FXML
    private Button butonSave;
    @FXML
    private TableColumn<String, Juego> titulo;
    @FXML
    private TableColumn<String, Juego> genero;
    @FXML
    private TableColumn<String, Juego> description;
    @FXML
    private TableColumn<String, Juego> desarrollador;
    @FXML
    private TableColumn<LocalDate, Juego> fechaLanzamiento;
    @FXML
    private TableView<Juego> listaJuegos;

    @Inject
    public JuegosController(JuegosViewModel viewmodel) {
        this.viewmodel = viewmodel;
    }

    @Override
    public void principalCargado() {
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        genero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        description.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        desarrollador.setCellValueFactory(new PropertyValueFactory<>("desarrollador"));
        fechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));

        viewmodel.setIdUsuarioLogueado(getPrincipalController().getUsername());
        viewmodel.cargarJuegos();
        viewmodel.cargarSuscripciones();
        observarState();
        listaJuegos.setOnMouseClicked(mouseEvent -> {
            Juego juegoSeleccionado = listaJuegos.getSelectionModel().getSelectedItem();
            if (juegoSeleccionado != null)
                seleccionarJuego(juegoSeleccionado);


        });
    }

    private void seleccionarJuego(Juego juegoSeleccionado) {
        if (juegoSeleccionado != null) {
            butonSuscribe.setDisable(false);
            if (viewmodel.verificarSuscripcion(juegoSeleccionado)) {
                butonSuscribe.setText("Suscrito");
            } else {
                butonSuscribe.setText("Suscribirse");
            }
        } else {
            butonSuscribe.setDisable(true);
        }
    }

    private void observarState() {
        viewmodel.getState().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> {
                    if (newValue.getError() != null) {
                        getPrincipalController().sacarAlertError(newValue.getError().getMensaje());
                    }

                    if (newValue.getJuegos() != null) {
                        listaJuegos.getItems().clear();
                        listaJuegos.getItems().addAll(newValue.getJuegos());
                    }

                    if (newValue.getSuscripciones() != null) {
                        butonSuscribe.setDisable(false);
                        if (newValue.getJuegoSeleccionado() != null) {
                            if (viewmodel.verificarSuscripcion(newValue.getJuegoSeleccionado())) {
                                butonSuscribe.setText("Suscrito");
                                getPrincipalController().sacarAlertInfo("Se ha suscrito al juego " + newValue.getJuegoSeleccionado().getTitulo() + " con exito");
                            } else {
                                butonSuscribe.setText("Suscribirse");
                                getPrincipalController().sacarAlertInfo("Se ha desuscrito del juego " + newValue.getJuegoSeleccionado().getTitulo() + " con exito");
                            }
                        }

                    } else {
                        butonSuscribe.setDisable(true);
                    }
                }));

    }

    public void actionSuscribe() {
        if (butonSuscribe.getText().equals("Suscribirse")) {
            viewmodel.suscribirse(listaJuegos.getSelectionModel().getSelectedItem());
        } else {
            viewmodel.desuscribirse(listaJuegos.getSelectionModel().getSelectedItem());
        }
    }

    public void actionSave() {
        viewmodel.saveSubscripcions();
    }

}
