package domain.modelo;

import lombok.Data;

@Data
public class MiEpisode {
    private final String title;
    private final int episodeNumber;
    private final int seriesEpisodeNumber;
    private final MiSeason season;


    /*private final MiCharacter[] mainCharacters; here it says the main characters of the episode so that is why I made it an array
      private final MiCharacter[] supportingCharacters;
      private final MiCharacter[] recurringCharacters;*/
}
