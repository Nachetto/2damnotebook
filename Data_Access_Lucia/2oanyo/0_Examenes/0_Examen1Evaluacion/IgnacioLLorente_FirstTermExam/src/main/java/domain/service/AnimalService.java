package domain.service;

import dao.model.Animal;
import dao.repository.AnimalDao;
import jakarta.inject.Inject;

public class AnimalService {
    private final AnimalDao dao;
    @Inject
    public AnimalService(AnimalDao dao) {
        this.dao = dao;
    }

    public int delete(Animal a, boolean confirmation) {
        return dao.delete(a, confirmation);
    }

    public Animal getFromUsername(String username) {
        return dao.getFromUsername(username);
    }

    public boolean hasAssociatedVisits(Animal a) {
        return dao.hasAssociatedVisits(a);
    }
}
