package dao.repository;

import dao.model.Weapon;

import java.util.List;

public interface WeaponsRepository {
    List<Weapon> getAll();
    Weapon get(int id);
    int update(Weapon faction);
    boolean delete(Weapon faction);
    int save(Weapon faction);
}
