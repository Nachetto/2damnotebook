package org.example.service;

import org.example.dao.CentroOcioDao;
import org.example.domain.CentroOcio;
import org.example.domain.ParqueAtracciones;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Service implements ServiceInterface{
    CentroOcioDao dao;
    public Service(){
        dao = new CentroOcioDao();
    }
    @Override
    public List<CentroOcio> getListaCentroOcioes() {
        return dao.getCentros();
    }

    @Override
    public List<CentroOcio> listadoOrdenadoProvinciaFecha() {
        return dao.listadoOrdenadoProvinciaFecha();
    }

    @Override
    public boolean addCentroOcio(CentroOcio centroOcio) {
        return dao.addCentro(centroOcio);
    }

    @Override
    public List<CentroOcio> consulta() {
        return dao.getCentros();
    }

    @Override
    public boolean actualizarServicios(int id, String servicioNuevo) {
        return dao.newService(id, servicioNuevo);
    }

    @Override
    public int removeCentrosOcio(int anyo) {
        return dao.removeCentrosOcio(anyo);
    }

    @Override
    public List<ParqueAtracciones> listadoOrdenado(int edad, boolean festivo) {
        return dao.listadoOrdenadoParqueAtracciones(edad, festivo);
    }

    public Map<String, List<CentroOcio>> mapa() {
        return dao.mapa();
    }



    public boolean guardarTXT(){
        return dao.guardarTXT();
    }

    public boolean escribirBinario() {
        return dao.escribirBinario();
    }

    public boolean escribirJSON() {
        return dao.escribirJSON();
    }

    public ParqueAtracciones[] leerJSON() {
        return dao.leerJSON();
    }

    public boolean leerBinario() {
        return dao.leerBinario();
    }

    public boolean cargarFicheroTexto() {
        return dao.cargarFicheroTexto();
    }
}
