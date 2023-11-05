package dao;

import domain.Pista;

import java.util.List;

public interface PistasDao{
    public List<Pista> getPistas();
    public boolean addPista(Pista p);
    public boolean removePista(Pista p);
}
