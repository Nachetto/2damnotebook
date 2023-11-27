package dao.impl;

import dao.DaoGenerico;
import dao.EpisodesDAO;
import dao.retrofit.modelo.ResponseEpisode;
import domain.modelo.MiEpisode;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.util.List;

public class EpisodesDAOImpl extends DaoGenerico implements EpisodesDAO {
    private final TheOfficeApi theOfficeApi;
    @Inject
    public EpisodesDAOImpl(TheOfficeApi theOfficeApi) {
        this.theOfficeApi = theOfficeApi;
    }
    @Override
    public Either<String, List<MiEpisode>> getAllEpisodes(int limit) {
        Call<ResponseEpisode> r = theOfficeApi.getAllEpisodes(limit);
        Either<String, ResponseEpisode> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiEpisode> res = filtradoAMiEpisode(responseEither.get());
            return Either.right(res);
        } else {
            return Either.left(responseEither.getLeft());
        }
    }

    @Override
    public Either<String, List<MiEpisode>> getAllEpisodes() {
        Call<ResponseEpisode> r = theOfficeApi.getAllEpisodes();
        Either<String, ResponseEpisode> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiEpisode> res = filtradoAMiEpisode(responseEither.get());
            return Either.right(res);
        } else {
            return Either.left(responseEither.getLeft());
        }
    }

    @Override
    public Either<String, List<MiEpisode>> getEpisodesBySeason(int season) {
        Call<ResponseEpisode> r = theOfficeApi.getEpisodesBySeason(season);
        Either<String, ResponseEpisode> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiEpisode> res = filtradoAMiEpisode(responseEither.get()).stream()
                    .filter(miEpisode -> miEpisode.getSeason() == season).toList() ;
            return Either.right(res);
        } else {
            return Either.left(responseEither.getLeft());
        }
    }

    private List<MiEpisode> filtradoAMiEpisode(ResponseEpisode responseEpisode) {
        return responseEpisode.getResults().stream()
                .map(resultsItem -> {
                    String title = resultsItem.getTitle();
                    int episodeNumber = resultsItem.getSeriesEpisodeNumber();
                    int season = resultsItem.getSeasonId();
                    String summary = resultsItem.getSummary();
                    return new MiEpisode(title, episodeNumber, season, summary);
                })
                .toList();
    }
}
