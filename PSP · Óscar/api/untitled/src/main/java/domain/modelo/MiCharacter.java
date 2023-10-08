package domain.modelo;

import lombok.Data;

@Data
public class MiCharacter {
    private final String gender;
    private final String characterName;
    private final String actorName;
    private final MiEpisode[] episodes;
    private final String[] job;
}
