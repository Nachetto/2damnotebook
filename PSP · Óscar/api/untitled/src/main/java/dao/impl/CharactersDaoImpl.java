package dao.impl;

import common.config.Configuracion;
import dao.CharactersDao;
import dao.DaoGenerico;
import dao.retrofit.ProducesRetrofit;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.Response;
import domain.modelo.MiCharacter;
import domain.modelo.MiEpisode;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.util.List;

public class CharactersDaoImpl extends DaoGenerico implements CharactersDao {
    private final TheOfficeApi theOfficeApi;
    private final ProducesRetrofit retrofit;
    private final Configuracion configuracion;

    @Inject
    public CharactersDaoImpl(Configuracion configuracion, TheOfficeApi theOfficeApi, ProducesRetrofit retrofit) {
        this.retrofit = retrofit;
        this.configuracion = configuracion;
        this.theOfficeApi = theOfficeApi;
    }

    @Override
    public Either<String, List<MiCharacter>> getAllCharacters() {

        //List<ResultsItem> res = theOfficeApi.getAllCharacters().execute().body().getResults();
        Call<Response> r = theOfficeApi.getAllCharacters();

        if (safeApicall(r).isRight()) {
            List<MiCharacter> res = safeApicall(r).get().getResults().stream().map(resultsItem -> new MiCharacter(resultsItem.getGender(),resultsItem.getName(),resultsItem.getActor(),new MiEpisode[]{},new String[]{})).toList();
            return Either.right(res);
        } else
            return Either.left(safeApicall(r).getLeft());

    }
    //metodo para cambiar de ResponseCharacter a MiCharacter


    @Override
    public Either<String, MiCharacter> getNamedCharacter(String CharacterName) {
        return null;
    }

    @Override
    public Either<String, MiCharacter> getNamedActor(String ActorName) {
        return null;
    }

    @Override
    public Either<String, List<MiCharacter>> getAllCharactersByGender(String genero) {
        return null;
    }

    @Override
    public Either<String, List<MiCharacter>> getAllCharactersByJob(String job) {
        return null;
    }

    @Override
    public List<String> getAllJobTypes() {
        return null;
    }
}
