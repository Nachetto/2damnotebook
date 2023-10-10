package service;

import common.config.Configuracion;
import dao.impl.CharactersDaoImpl;
import dao.retrofit.llamadas.TheOfficeApi;
import dao.retrofit.modelo.ResultsItem;
import domain.modelo.MiCharacter;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class CharactersService {
    CharactersDaoImpl dao;

    @Inject
    public CharactersService(CharactersDaoImpl dao) {
        this.dao = dao;
    }

    public Either<String, List<MiCharacter>> getAllCharacters() {
        return dao.getAllCharacters();
        //aqui el metodo para pasarlo a mi character
    }

    public Either<String, ResultsItem> getNamedCharacter(String CharacterName) {
        return dao.getNamedCharacter(CharacterName);
        //return dao.getNamedCharacter(CharacterName);
    }

    public Either<String, MiCharacter> getNamedActor(String ActorName) {
        return dao.getNamedActor(ActorName);
    }

    public Either<String, List<MiCharacter>> getAllCharactersByGender(String genero) {


        return dao.getAllCharactersByGender(genero);
    }

    public Either<String, List<MiCharacter>> getAllCharactersByJob(String job) {
        return dao.getAllCharactersByJob(job);
    }

    public List<String> getAllJobTypes() {
        return dao.getAllJobTypes();
    }

















//        Either<String, Response> respuesta = null;
//        if (safeApicall(r).isRight()) {
//            Response personajesacambiar = safeApicall(r).get();
//            return Either.right(personajesacambiar.getResults());
//            //aqui tengo que cambiar de ResponseCharacter a MiCharacter
//           Response personajesacambiar = safeApicall(r).get();
//            for (ResponseCharacter personajeacambiar : safeApicall(r)) {
//                MiEpisode[] episodes = new MiEpisode[personajeacambiar.getEpisodes().length];
//                int i = 0;
//                for (ResponseEpisode e : personajeacambiar.getEpisodes()) {
//                    episodes[i] = new MiEpisode(e.getTitle(),
//                            Integer.parseInt(e.getEpisode()),
//                            e.getSeriesEpisodeNumber(),
//                            new MiSeason(e.getSeason().getId(), LocalDate.parse(e.getSeason().getStartDate())));
//                    i++;
//                }
//                personajes.add(
//                        new MiCharacter(personajeacambiar.getGender(),
//                                personajeacambiar.getName(),
//                                personajeacambiar.getActor(),
//                                episodes, personajeacambiar.getJob()));
//                return Either.right(personajes);
//            }
//        } else
//            return Either.left(safeApicall(r).getLeft());
}
