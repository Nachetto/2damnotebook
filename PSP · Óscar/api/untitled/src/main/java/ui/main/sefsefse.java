package ui.main;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import service.CharactersService;
import service.EpisodesService;

public class sefsefse {
    private final CharactersService sv;

    @Inject
    public sefsefse(CharactersService sv) {
        this.sv = sv;
    }

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        EpisodesService sv = container.select(EpisodesService.class).get();
        System.out.printf(sv.getAllEpisodes().toString());


    }
}
