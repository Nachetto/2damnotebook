package service;

import dao.impl.CharactersDAOImpl;
import domain.modelo.MiCharacter;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class CharactersService {
    CharactersDAOImpl dao;

    @Inject
    public CharactersService(CharactersDAOImpl dao) {
        this.dao = dao;
    }

    public Either<String, List<MiCharacter>> getAllCharacters() {
        return dao.getAllCharacters();
    }

    public Either<String, List<MiCharacter>> getAllCharacters(int limit){
        return dao.getAllCharacters(limit);
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
