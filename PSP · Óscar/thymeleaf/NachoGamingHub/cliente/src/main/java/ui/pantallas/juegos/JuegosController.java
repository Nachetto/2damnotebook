package ui.pantallas.juegos;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.pantallas.common.BasePantallaController;

public class JuegosController extends BasePantallaController {
    JuegosViewModel viewmodel;
    @FXML
    private Button butonSuscribe;
    @FXML
    private Button butonSave;
    @FXML
    private TableColumn titulo;
    @FXML
    private TableColumn genero;
    @FXML
    private TableColumn description;
    @FXML
    private TableColumn desarrollador;
    @FXML
    private TableColumn fechaLanzamiento;
    @FXML
    private TableView listaEpisodios;

    {
        //cambiar el boton de suscribirse a suscrito y viceversa
        if (newValue.contains(listaEpisodios.getSelectionModel().getSelectedItem())) {
            butonSuscribe.setText("Suscrito");
        } else {
            butonSuscribe.setText("Suscribirse");
        }
    }

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
                if (newValue.getSuscripciones()!=null && newValue.getSuscripciones().contains(listaEpisodios.getSelectionModel().getSelectedItem())){
                    butonSuscribe.setText("Suscrito");
                } else{
                    butonSuscribe.setText("Suscribirse");
                }
            });

        });
    }

//cargar todos los juegos


//cargar suscripciones del usuario a la lista del state, gestionar listeners del tableview para que cuando se suscriba a un juego se actualice la lista de suscripciones en el viewmodel

//cambiar el boton de suscribirse a suscrito y viceversa basandote en cuando observas el state

//cambiar el boton de suscribirse a suscrito y viceversa

}
