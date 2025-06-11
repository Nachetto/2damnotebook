package domain.modelo;

import lombok.Data;

@Data
public class MiEpisode {
    private final String title;
    private final int episodeNumber;
    private final int season;
    private final String summary;
}
