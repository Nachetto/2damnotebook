package dao;

import domain.modelo.MiCharacter;
import io.vavr.control.Either;

import java.util.List;

public interface CharactersDAO {
    Either<String, List<MiCharacter>> getAllCharacters(int limit);
}
