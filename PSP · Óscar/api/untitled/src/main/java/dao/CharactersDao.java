package dao;

import dao.retrofit.modelo.ResponseCharacter;
import dao.retrofit.modelo.ResultsItem;
import domain.modelo.MiCharacter;
import io.vavr.control.Either;

import java.util.List;

public interface CharactersDao {
    Either<String, List<ResultsItem>> getAllCharacters();
    Either<String, MiCharacter> getNamedCharacter(String CharacterName);
    Either<String, MiCharacter> getNamedActor(String ActorName);
    Either<String, List<MiCharacter>> getAllCharactersByGender(String genero);
    Either<String, List<MiCharacter>> getAllCharactersByJob(String job);
    List<String> getAllJobTypes();
}
