package dao;

import domain.modelo.MiSeason;
import io.vavr.control.Either;

import java.util.List;

public interface SeasonsDAO {
    Either<String, List<MiSeason>> getAllSeasons();
}
