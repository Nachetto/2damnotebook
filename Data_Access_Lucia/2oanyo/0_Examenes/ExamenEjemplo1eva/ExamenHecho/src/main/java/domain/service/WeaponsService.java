package domain.service;

import dao.repository.WeaponsRepository;
import dao.model.Weapon;
import jakarta.inject.Inject;

import java.util.List;

public class WeaponsService {
    private final WeaponsRepository dao;
    @Inject
    public WeaponsService(WeaponsRepository dao) {
        this.dao = dao;
    }

    public List<Weapon> getAll() {
        return dao.getAll();
    }

    public Weapon get(int id) {
        return dao.get(id);
    }

    public int update(Weapon faction) {
        return dao.update(faction);
    }

    public boolean delete(Weapon faction) {
        return dao.delete(faction);
    }

    public int save(Weapon faction) {
        return dao.save(faction);
    }
}
