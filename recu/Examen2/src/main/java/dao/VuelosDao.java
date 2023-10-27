package dao;

import domain.Vuelo;

import java.util.List;

public interface VuelosDao {

    public List<Vuelo> getVuelos();
    public boolean addVuelo(Vuelo v);
    public boolean removeVuelo(Vuelo v);
}
