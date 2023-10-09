package service;

import common.config.Configuracion;
import dao.impl.CharactersDaoImpl;
import dao.retrofit.llamadas.TheOfficeApi;
import domain.modelo.MiCharacter;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class CharactersService {
    CharactersDaoImpl dao;

    @Inject
    public CharactersService(TheOfficeApi theOfficeApi, Configuracion configuracion) {
        dao = new CharactersDaoImpl(configuracion, theOfficeApi);
    }

    public Either<String, List<MiCharacter>> getAllCharacters() {
        return dao.getAllCharacters();
    }

    public Either<String, MiCharacter> getNamedCharacter(String CharacterName) {
        return dao.getNamedCharacter(CharacterName);
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
}
