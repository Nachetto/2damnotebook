package domain.modelo;

import lombok.Data;

@Data
public class MiEpisode {
    private final String title;
    private final String episodeNumber;
    private final String seriesEpisodeNumber;
    private final String AirDate;
    private final MiSeason season;
    private final MiCharacter[] mainCharacters; //here it says the main characters of the episode so that is why I made it an array
    private final MiCharacter[] supportingCharacters;
    private final MiCharacter[] recurringCharacters;
}
