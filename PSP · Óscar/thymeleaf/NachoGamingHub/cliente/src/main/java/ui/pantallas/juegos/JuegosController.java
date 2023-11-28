package ui.pantallas.juegos;

import domain.modelo.Juego;
import domain.modelo.Suscripcion;
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
import java.util.List;

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
    private TableView<Juego> listaEpisodios;

    @Inject
    public JuegosController(JuegosViewModel viewmodel) {
        this.viewmodel = viewmodel;
    }

    @Override
    public void principalCargado() {
        //dar valores a la tabla
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        genero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        desarrollador.setCellValueFactory(new PropertyValueFactory<>("desarrollador"));
        fechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));

        viewmodel.setIdUsuarioLogueado(getPrincipalController().getUsername());
        //cargar todos los juegos
        viewmodel.cargarJuegos();
        //cargar suscripciones del usuario a la lista del state, gestionar listeners del tableview para que cuando se suscriba a un juego se actualice la lista de suscripciones en el viewmodel
        viewmodel.cargarSuscripciones();
        //observar el state
        observarState();
    }

    private void observarState() {
        viewmodel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() ->{//cambiar el boton de suscribirse a suscrito y viceversa
                if (newValue.getSuscripciones()!=null){
                    //iteras sobre la lista de suscripciones y si el juego seleccionado esta en la lista cambias el boton a suscrito
                    for (Suscripcion sub : newValue.getSuscripciones()){
                        if (sub.getUuid().equals(newValue.getJuegoSeleccionado().getUuid())){
                            butonSuscribe.setText("Suscrito");
                        }
                    }
                    butonSuscribe.setText("Suscrito");
                } else{
                    butonSuscribe.setText("Suscribirse");
                }
            });

        });
    }

    public void seleccionarJuego(MouseEvent mouseEvent) {
        viewmodel.seleccionarJuego(listaEpisodios.getSelectionModel().getSelectedItem());
        if (viewmodel.verificarSuscripcion()){
            butonSuscribe.setText("Suscrito");
        } else{
            butonSuscribe.setText("Suscribirse");
        }
    }

    public void actionSuscribe(MouseEvent mouseEvent) {
    }

    public void actionSave(MouseEvent mouseEvent) {

    }

//cargar todos los juegos


//cargar suscripciones del usuario a la lista del state, gestionar listeners del tableview para que cuando se suscriba a un juego se actualice la lista de suscripciones en el viewmodel

//cambiar el boton de suscribirse a suscrito y viceversa basandote en cuando observas el state

//cambiar el boton de suscribirse a suscrito y viceversa

}
