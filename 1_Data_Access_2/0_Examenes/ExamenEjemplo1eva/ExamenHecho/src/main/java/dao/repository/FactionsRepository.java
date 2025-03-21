package dao.repository;

import dao.model.Faction;

import java.util.List;

public interface FactionsRepository {
    List<Faction> getAll();
    Faction get(String name);
    int update(Faction faction);
    boolean delete(Faction faction);
    int save(Faction faction);
}
