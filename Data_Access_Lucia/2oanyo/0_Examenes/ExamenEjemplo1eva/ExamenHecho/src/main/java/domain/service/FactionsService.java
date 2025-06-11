package domain.service;

import dao.repository.FactionsRepository;
import jakarta.inject.Inject;

public class FactionsService {
    private final FactionsRepository dao;
    private final WeaponsService weaponsService;
    @Inject
    public FactionsService(FactionsRepository dao, WeaponsService weaponsService) {
        this.dao = dao;
        this.weaponsService = weaponsService;
    }

    public int exerciseOne(){
        dao.getAll().forEach(dao::save);
        return 1;
    }
}
