package org.example.service;

import org.example.common.FechaException;
import org.example.dao.VueloDaoImpl;
import org.example.domain.Vuelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VueloServiceImpl implements ServiceInterface {
    VueloDaoImpl vueloDaoImpl = new VueloDaoImpl();

    @Override
    public List<Vuelo> getListaVuelos() {
        return vueloDaoImpl.getListaVuelos();
    }

    @Override
    public List<Vuelo> listadoOrdenadoPrecioPasajeros(String origen, String destino) {
        return vueloDaoImpl.listadoOrdenadoPrecioPasajeros(origen,destino);
    }

    @Override
    public boolean addVuelo(Vuelo vuelo) throws FechaException {
        return vueloDaoImpl.addVuelo(vuelo);
    }
    public Vuelo filtrandoNuevoVuelo(String tipo, int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha, List<String> nuevaescala){
        return  vueloDaoImpl.filtrandoNuevoVuelo(tipo, id, maxpasajeros, precioavg, aereolinea, origen, destino, fecha, nuevaescala);
    }

        @Override
    public List<Vuelo> consulta(String origen, String destino, double precio1, double precio2) {
        return vueloDaoImpl.consulta(origen,destino,precio1,precio2);
    }

    @Override
    public boolean addEscala(int id, String pais) {
        return vueloDaoImpl.addEscala(id,pais);
    }

    @Override
    public boolean removeVuelo(String origen, String destino) {
        return vueloDaoImpl.removeVuelo(origen, destino);
    }

    @Override
    public Map<String, List<Vuelo>> getVuelosOrigen() {
        return vueloDaoImpl.getVuelosOrigen();
    }

    @Override
    public List<Vuelo> cargarFichero() {
        return vueloDaoImpl.cargarFichero();
    }

    @Override
    public boolean escribirFichero() {
        return vueloDaoImpl.escribirFichero();
    }

    @Override
    public boolean escribirFicheroBinario() {
        return vueloDaoImpl.escribirFicheroBinario();
    }

    @Override
    public List<Vuelo> cargarFicheroBinario() {
        return vueloDaoImpl.cargarFicheroBinario();
    }

    public void guardarListaDesdeTextoGuardado(List<Vuelo> cargarFichero) {
        vueloDaoImpl.guardarListaDesdeTextoGuardado(cargarFichero);
    }

    public void guardarListaDesdeBinarioGuardado(List<Vuelo> cargarFicheroBinario) {
        vueloDaoImpl.guardarListaDesdeBinarioGuardado(cargarFicheroBinario);
    }

    public void escribirJSON(){
        vueloDaoImpl.escribirJSON();
    }

    public List<Vuelo> cargarJSON() {
        return vueloDaoImpl.cargarJSON();
    }

    public void guardarListaDesdeJSONGuardado(List<Vuelo> cargarJSON) {
        vueloDaoImpl.guardarListaDesdeJSONGuardado(cargarJSON);
    }
}
