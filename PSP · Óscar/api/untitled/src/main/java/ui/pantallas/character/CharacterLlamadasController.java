package ui.pantallas.character;

import domain.modelo.MiCharacter;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CharactersService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.util.List;

public class CharacterLlamadasController extends BasePantallaController {
    private final CharactersService service;
    @FXML
    private TableView<MiCharacter> listaPersonajes;
    @FXML
    private TableColumn<MiCharacter, String> gender;
    @FXML
    private TableColumn<MiCharacter, String> characterName;
    @FXML
    private TableColumn<MiCharacter, String> actorName;
    @FXML
    private TableColumn<MiCharacter, List<String>> job;

    @Inject
    public CharacterLlamadasController(CharactersService service) {
        this.service = service;
    }

    @Override
    public void principalCargado() {
        llamadasSegunParametrosDeBusqueda();
    }

    private void llamadasSegunParametrosDeBusqueda() {
        try {
            borrarTabla();
            int limiteBusqueda = getPrincipalController().getParametrosBusquedaCharacter().getLimite();
            if (limiteBusqueda == 83)
                listaPersonajes.getItems().addAll(service.getAllCharacters(limiteBusqueda).get());
            else
                listaPersonajes.getItems().addAll(service.getAllCharacters(limiteBusqueda).get());
        } catch (Exception e) {
            getPrincipalController().sacarAlertError(ConstantesPantallas.ERROR_BUSQUEDA + " " + e.getMessage());
        }
    }

    private void borrarTabla() {
        listaPersonajes.getItems().clear();
    }

    public void initialize() {
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        characterName.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        actorName.setCellValueFactory(new PropertyValueFactory<>("actorName"));
        job.setCellValueFactory(new PropertyValueFactory<>("job"));
    }

}
