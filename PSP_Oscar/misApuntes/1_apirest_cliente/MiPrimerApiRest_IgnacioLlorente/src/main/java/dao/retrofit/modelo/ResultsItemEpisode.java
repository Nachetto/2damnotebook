package dao.retrofit.modelo;

import lombok.Data;

@Data
public class ResultsItemEpisode {
    private String summary;
    private int seriesEpisodeNumber;
    private int seasonId;
    private String airDate;
    private String episode;
    private int id;
    private String title;
}
