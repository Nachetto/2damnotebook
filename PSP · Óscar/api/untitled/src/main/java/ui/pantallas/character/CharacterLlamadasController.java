package ui.pantallas.character;

import jakarta.inject.Inject;
import ui.pantallas.common.BasePantallaController;
import service.CharactersService;

public class CharacterLlamadasController extends BasePantallaController {
    private CharactersService service;
    @Inject
    public CharacterLlamadasController(CharactersService service){
        this.service = service;
    }
    @Override
    public void principalCargado() {

    }
    public void initialize() {
    }

}
