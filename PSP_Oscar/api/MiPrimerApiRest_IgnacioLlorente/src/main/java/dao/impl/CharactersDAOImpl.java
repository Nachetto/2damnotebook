package dao.impl;

import dao.CharactersDAO;
import dao.DaoGenerico;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.ResponseCharacter;
import domain.modelo.MiCharacter;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.util.List;
import java.util.stream.Collectors;

public class CharactersDAOImpl extends DaoGenerico implements CharactersDAO {
    private final TheOfficeApi theOfficeApi;

    @Inject
    public CharactersDAOImpl(TheOfficeApi theOfficeApi) {
        this.theOfficeApi = theOfficeApi;
    }

    @Override
    public Either<String, List<MiCharacter>> getAllCharacters(int limit) {
        Call<ResponseCharacter> r = theOfficeApi.getAllCharacters(limit);
        Either<String, ResponseCharacter> responseEither = safeApicall(r);
        if (responseEither.isRight()) {
            List<MiCharacter> res = filtradoAMiCharacter(responseEither.get());
            return Either.right(res);
        } else {
            return Either.left(responseEither.getLeft());
        }
    }

    private List<MiCharacter> filtradoAMiCharacter(ResponseCharacter responseCharacter) {
        return responseCharacter.getResults().stream()
                .map(resultsItem -> {
                    String gender = resultsItem.getGender();
                    String characterName = resultsItem.getName();
                    String actorName = resultsItem.getActor();
                    // Mapear la lista de trabajos (job) a List<String>
                    List<String> job = resultsItem.getJob().stream()
                            .map(jobItem -> jobItem.toString())
                            .collect(Collectors.toList());
                    // Crear un objeto MiCharacter con los valores mapeados
                    return new MiCharacter(gender, characterName, actorName, job);
                })
                .toList();
    }
}
