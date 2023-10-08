package dao.impl;

import common.config.Configuracion;
import dao.CharactersDao;
import dao.DaoGenerico;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.ResponseCharacter;
import dao.retrofit.modelo.ResponseEpisode;
import domain.modelo.MiCharacter;
import domain.modelo.MiEpisode;
import domain.modelo.MiSeason;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import retrofit2.Call;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CharactersDaoImpl extends DaoGenerico implements CharactersDao {
    private final TheOfficeApi theOfficeApi;

    private final Configuracion configuracion;

    @Inject
    public CharactersDaoImpl(Configuracion configuracion, TheOfficeApi theOfficeApi) {
        this.configuracion = configuracion;
        this.theOfficeApi = theOfficeApi;
    }

    @Override
    public Either<String, List<MiCharacter>> getAllCharacters() {
        Either<String, List<MiCharacter>> respuesta = null;
        Call<ResponseCharacter> r = theOfficeApi.getAllCharacters();
        List<MiCharacter> personajes = new ArrayList<>();
        if (safeApicall(r).isRight()) {
            //aqui tengo que cambiar de ResponseCharacter a MiCharacter
            for (ResponseCharacter personajeacambiar : safeApicall(r)) {
                MiEpisode[] episodes = new MiEpisode[personajeacambiar.getEpisodes().length];
                int i = 0;
                for (ResponseEpisode e : personajeacambiar.getEpisodes()) {
                    episodes[i] = new MiEpisode(e.getTitle(),
                            Integer.parseInt(e.getEpisode()),
                            e.getSeriesEpisodeNumber(),
                            new MiSeason(e.getSeason().getId(), LocalDate.parse(e.getSeason().getStartDate())));
                    i++;
                }
                personajes.add(
                        new MiCharacter(personajeacambiar.getGender(),
                                personajeacambiar.getName(),
                                personajeacambiar.getActor(),
                                episodes, personajeacambiar.getJob()));
                respuesta = Either.right(personajes);
            }
        } else
            respuesta = Either.left(safeApicall(r).getLeft());
        return respuesta;
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
