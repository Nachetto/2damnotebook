package dao;

import domain.modelo.MiEpisode;
import io.vavr.control.Either;

import java.util.List;

public interface EpisodesDAO {
    Either<String, List<MiEpisode>> getAllEpisodes(int limit);
    Either<String, List<MiEpisode>> getAllEpisodes();
    Either<String, List<MiEpisode>> getEpisodesBySeason(int season);
}
