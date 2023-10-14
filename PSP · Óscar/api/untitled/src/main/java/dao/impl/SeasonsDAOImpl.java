package dao.impl;

import dao.DaoGenerico;
import dao.SeasonsDAO;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.ResponseSeason;
import domain.modelo.MiSeason;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.time.LocalDate;
import java.util.List;

public class SeasonsDAOImpl extends DaoGenerico implements SeasonsDAO {
    private final TheOfficeApi theOfficeApi;
    @Inject
    public SeasonsDAOImpl(TheOfficeApi theOfficeApi) {
        this.theOfficeApi = theOfficeApi;
    }
    @Override
    public Either<String, List<MiSeason>> getAllSeasons() {
        Call<ResponseSeason> r = theOfficeApi.getAllSeasons();
        Either<String, ResponseSeason> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiSeason> res = filtradoAMiEpisode(responseEither.get());
            return Either.right(res);
        } else {
            return Either.left(responseEither.getLeft());
        }
    }

    private List<MiSeason> filtradoAMiEpisode(ResponseSeason responseSeason) {
        return responseSeason.getResults().stream()
                .map(resultsItem -> {
                    LocalDate fecha = LocalDate.parse(resultsItem.getStartDate());
                    int number = resultsItem.getNumber();
                    return new MiSeason(number, fecha);
                })
                .toList();
    }
}
