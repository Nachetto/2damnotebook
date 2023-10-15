package service;

import dao.impl.EpisodesDAOImpl;
import domain.modelo.MiEpisode;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.util.List;

public class EpisodesService {
    EpisodesDAOImpl dao;
    @Inject
    public EpisodesService(EpisodesDAOImpl dao) {
        this.dao = dao;
    }

    public Either<String, List<MiEpisode>> getAllEpisodes(int limit) {
        return dao.getAllEpisodes(limit);
    }

    public Either<String, List<MiEpisode>> getAllEpisodes() {
        return dao.getAllEpisodes();
    }

    public Either<String, List<MiEpisode>> getEpisodesBySeason(int season) {
        return dao.getEpisodesBySeason(season);
    }
}
