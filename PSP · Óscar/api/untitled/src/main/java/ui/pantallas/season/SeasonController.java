package ui.pantallas.season;

import domain.modelo.MiSeason;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SeasonsService;
import ui.pantallas.common.BasePantallaController;

import java.time.LocalDate;

public class SeasonController extends BasePantallaController {
    @FXML
    private TableColumn<LocalDate,MiSeason> number;
    @FXML
    private TableColumn<LocalDate,MiSeason> fecha;
    @FXML
    private TableView<MiSeason> listaEpisodes;
    @FXML
    private Spinner<Integer> spinner;

    private final SeasonsService service;

    @Inject
    public SeasonController(SeasonsService service) {
        this.service = service;
    }
    @Override
    public void principalCargado() {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 1));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    private void borrarTabla(){
        listaEpisodes.getItems().clear();
    }
    public void aplicarCambios() {
    borrarTabla();
        listaEpisodes.getItems().addAll(service.getAllSeasons().get());
    }
}
