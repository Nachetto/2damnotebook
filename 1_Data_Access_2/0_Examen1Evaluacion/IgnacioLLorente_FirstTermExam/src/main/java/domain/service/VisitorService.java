package domain.service;

import dao.model.Visitor;
import dao.repository.VisitorDao;
import domain.error.InternalServerErrorException;
import jakarta.inject.Inject;

public class VisitorService {
    private final VisitorDao dao;
    @Inject
    public VisitorService(VisitorDao dao) {
        this.dao = dao;
    }

    public int add(Visitor v) throws InternalServerErrorException {
        return dao.add(v);
    }
}
