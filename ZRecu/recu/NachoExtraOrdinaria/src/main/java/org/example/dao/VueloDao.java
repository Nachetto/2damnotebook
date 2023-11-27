package org.example.dao;

import org.example.common.FechaException;
import org.example.domain.Vuelo;

import java.util.List;
import java.util.Map;

public interface VueloDao {
    List<Vuelo> getListaVuelos();
    List<Vuelo> listadoOrdenadoPrecioPasajeros(String origen, String destino);
    boolean addVuelo(Vuelo vuelo) throws FechaException;
    List<Vuelo> consulta(String origen, String destino, double precio1, double precio2);
    boolean addEscala(int id, String pais);
    boolean removeVuelo(String origen, String destino);
    Map<String,List<Vuelo>> getVuelosOrigen();
    List<Vuelo> cargarFichero();
    boolean escribirFichero();
    boolean escribirFicheroBinario();
    List<Vuelo> cargarFicheroBinario();
}

