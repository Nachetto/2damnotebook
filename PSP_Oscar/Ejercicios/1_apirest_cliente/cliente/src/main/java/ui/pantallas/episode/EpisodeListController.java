package ui.pantallas.episode;

import domain.modelo.MiEpisode;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EpisodesService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;


public class EpisodeListController extends BasePantallaController {
    private final EpisodesService service;

    @FXML
    private TableColumn<Integer, MiEpisode> season;
    @FXML
    private TableColumn<Integer, MiEpisode> episodeNumber;
    @FXML
    private TableColumn<String, MiEpisode> title;
    @FXML
    private TableColumn<String, MiEpisode> summary;
    @FXML
    private TableView<MiEpisode> listaEpisodios;

    @Inject
    public EpisodeListController(EpisodesService service) {
        this.service = service;
    }

    @Override
    public void principalCargado() {
        llamadasSegunParametrosDeBusqueda();
    }

    private void llamadasSegunParametrosDeBusqueda() {
        try {
            borrarTabla();
            if (getPrincipalController().getParametrosBusquedaEpisode()== null) {
                if (service.getAllEpisodes(200).isRight())
                    listaEpisodios.getItems().addAll(service.getAllEpisodes(200).get());
                else
                    getPrincipalController().sacarAlertError(service.getAllEpisodes(200).getLeft());
            } else {
                String tipoBusqueda = getPrincipalController().getParametrosBusquedaEpisode().getTipoBusqueda();
                String argumentoBusqueda = getPrincipalController().getParametrosBusquedaEpisode().getArgumentoBusqueda();
                int limite = getPrincipalController().getParametrosBusquedaEpisode().getLimite();
                if (tipoBusqueda.equalsIgnoreCase("Empty Search")) {
                    if (service.getAllEpisodes(limite).isRight())
                        listaEpisodios.getItems().addAll(service.getAllEpisodes(limite).get());
                    else
                        getPrincipalController().sacarAlertError(service.getAllEpisodes(limite).getLeft());
                } else if (tipoBusqueda.equalsIgnoreCase("Season")) {
                    if (service.getEpisodesBySeason(Integer.parseInt(argumentoBusqueda)).isRight())
                        listaEpisodios.getItems().addAll(service.getEpisodesBySeason(Integer.parseInt(argumentoBusqueda)).get());
                    else
                        getPrincipalController().sacarAlertError(service.getEpisodesBySeason(limite).getLeft());
                }
            }
            } catch(Exception e){
                getPrincipalController().sacarAlertError(ConstantesPantallas.ERROR_BUSQUEDA);
            }
        }

        private void borrarTabla () {
            listaEpisodios.getItems().clear();
        }

        public void initialize () {
            season.setCellValueFactory(new PropertyValueFactory<>("season"));
            episodeNumber.setCellValueFactory(new PropertyValueFactory<>("episodeNumber"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            summary.setCellValueFactory(new PropertyValueFactory<>("summary"));
        }
    }
