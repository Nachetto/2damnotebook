package dao.impl;

import dao.DaoGenerico;
import dao.SeasonsDAO;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.ResponseSeason;
import dao.retrofit.modelo.ResultsItemSeason;
import domain.modelo.MiSeason;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeasonsDAOImpl extends DaoGenerico implements SeasonsDAO {
    private final TheOfficeApi theOfficeApi;
    @Inject
    public SeasonsDAOImpl(TheOfficeApi theOfficeApi) {
        this.theOfficeApi = theOfficeApi;
    }
    @Override
    public Either<String, List<MiSeason>> getAllSeasons() {
        Call<List<ResultsItemSeason>> r = theOfficeApi.getAllSeasons();
        Either<String, List<ResultsItemSeason>> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiSeason> res = filtradoAMiSeason(responseEither.get());
            return Either.right(res);
        }else {
            return Either.left(responseEither.getLeft());
        }
    }

    private List<MiSeason> filtradoAMiSeason(List<ResultsItemSeason> responseSeasonList) {
        return responseSeasonList.stream()
                .map(resultsItem -> {
                    LocalDate fecha = LocalDate.parse(resultsItem.getStartDate());
                    int number = resultsItem.getNumber();
                    return new MiSeason(number, fecha);
                })
                .collect(Collectors.toList());
    }

}
