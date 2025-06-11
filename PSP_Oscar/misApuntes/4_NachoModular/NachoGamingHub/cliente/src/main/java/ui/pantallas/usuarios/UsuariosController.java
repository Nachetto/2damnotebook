package ui.pantallas.usuarios;

import domain.modelo.Usuario;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;
import java.util.UUID;

public class UsuariosController extends BasePantallaController {
    @FXML
    private TableView<Usuario> listaUsuarios;
    @FXML
    private TableColumn<UUID, Usuario> uuid;
    @FXML
    private TableColumn<String, Usuario> nombre;
    @FXML
    private TableColumn<String, Usuario> correoElectronico;
    @FXML
    private TableColumn<LocalDate, Usuario> fechaNacimiento;
    private final UsuariosViewModel viewmodel;
    @Inject
    public UsuariosController(UsuariosViewModel viewmodel) {
        this.viewmodel = viewmodel;
    }

    @Override
    public void principalCargado() {
        uuid.setCellValueFactory(new PropertyValueFactory<>("uuid"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        correoElectronico.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        viewmodel.cargarArticulos();
        observarState();
    }

    private void observarState() {
        viewmodel.getState().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.getUsuarios() != null) {
                listaUsuarios.getItems().clear();
                listaUsuarios.getItems().addAll(newValue.getUsuarios());
            }
        }));
    }
}
