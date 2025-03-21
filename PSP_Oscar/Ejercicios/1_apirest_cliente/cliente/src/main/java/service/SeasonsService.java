package service;

import dao.impl.SeasonsDAOImpl;
import domain.modelo.MiSeason;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class SeasonsService {
    SeasonsDAOImpl dao;
    @Inject
    public SeasonsService(SeasonsDAOImpl dao) {
        this.dao = dao;
    }

    public Either<String, List<MiSeason>> getAllSeasons() {
        return dao.getAllSeasons();
    }
}
