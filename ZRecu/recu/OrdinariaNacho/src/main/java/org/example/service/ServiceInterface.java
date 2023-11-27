package org.example.service;

import org.example.common.DificultadExcepcion;
import org.example.domain.Pista;
import org.example.domain.SkiFondo;
import org.example.domain.SkipAlpino;

import java.util.List;
import java.util.Map;

public interface ServiceInterface {
    List<Pista> getListaPistas();
    List<Pista> listadoOrdenadoProvinciaKm(String provincia);
    boolean addPista(SkiFondo pista);
    public boolean addPista(SkipAlpino pista)throws DificultadExcepcion;
    int consulta(String provincia);
    boolean addPuebloListaPueblos(int id, String pueblo);
    boolean removePista(int id);
    String getPistasProvincia();
    void cargarFichero();
    boolean escribirFichero();
    boolean escribirFicheroBinario();
    void cargarFicheroBinario();
}