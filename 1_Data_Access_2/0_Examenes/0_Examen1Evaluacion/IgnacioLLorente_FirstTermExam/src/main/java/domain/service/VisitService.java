package domain.service;

import dao.repository.VisitDao;
import domain.error.InternalServerErrorException;
import jakarta.inject.Inject;

public class VisitService {
    private final VisitDao dao;
    @Inject
    public VisitService(VisitDao dao){
        this.dao = dao;
    }

    public int loadFromXmlToDatabase() throws InternalServerErrorException {
        return dao.loadFromXmlToDatabase();
    }
}
