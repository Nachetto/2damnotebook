package dao.retrofit.modelo;

import lombok.Data;

@Data
public class ResponseEpisode {
    private final String id;
    private final String title;
    private final String summary;
    private final String episode; //somewhat this is the episode number
    private final int seriesEpisodeNumber;
    private final String airDate;
    private final ResponseSeason season;
    private final ResponseCharacter[] mainCharacters; //here it says the main characters of the episode so that is why I made it an array
    private final ResponseCharacter[] supportingCharacters;
    private final ResponseCharacter[] recurringCharacters;
}
