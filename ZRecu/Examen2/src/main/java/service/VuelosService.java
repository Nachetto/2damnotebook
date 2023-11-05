package service;

import dao.VuelosDaoImpl;
import domain.Vuelo;

import java.util.List;

public class VuelosService{
    VuelosDaoImpl dao;

    public VuelosService() {
        this.dao = new VuelosDaoImpl();
    }

    public List<Vuelo> getVuelos() {
        return dao.getVuelos();
    }

    public boolean addVuelo(Vuelo v) {
        return dao.addVuelo(v);
    }

    public boolean removeVuelo(Vuelo v) {
        return dao.removeVuelo(v);
    }

    public List<Vuelo> vuelosPorRango(int preciominimo, int preciomaximo) {
        return dao.vuelosPorRango(preciominimo, preciomaximo);
    }

    public boolean nuevaEscalaPorId(int id, String escala) {
        return dao.nuevaEscalaPorId(id, escala);
    }

    public boolean eliminarPorOrigenYDestino(String origen, String destino) {
        return dao.eliminarPorOrigenYDestino(origen, destino);
    }

    public List<Vuelo> vuelosPorPrecio() {
        return dao.vuelosPorPrecio();
    }

    public boolean escribirTXT() {
        return dao.escribirTXT();
    }

    public boolean escribirBinario() {
        return dao.escribirBinario();
    }

    public boolean cargarBinario() {
        return dao.cargarBinario();
    }
}
