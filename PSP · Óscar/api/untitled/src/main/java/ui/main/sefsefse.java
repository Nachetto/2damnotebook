package ui.main;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import service.CharactersService;

public class sefsefse {
    private final CharactersService sv;

    @Inject
    public sefsefse(CharactersService sv) {
        this.sv = sv;
    }

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        CharactersService sv = container.select(CharactersService.class).get();

        System.out.println(sv.getAllCharacters().toString());

    }
}
