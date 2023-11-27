package org.example.service;

import org.example.common.DificultadExcepcion;
import org.example.dao.PistaDaoImpl;
import org.example.domain.Pista;
import org.example.domain.SkiFondo;
import org.example.domain.SkipAlpino;

import java.util.List;
import java.util.Map;

public class PistaService implements ServiceInterface{
    PistaDaoImpl pistaDao;
    public PistaService(){
        pistaDao= new PistaDaoImpl();
    }


    public void agregarValoresIniciales() {
        pistaDao.agregarValoresIniciales();
    }

    @Override
    public List<Pista> getListaPistas() {
        return pistaDao.getListaPistas();
    }

    @Override
    public List<Pista> listadoOrdenadoProvinciaKm(String provincia) {
        return pistaDao.listadoOrdenadoProvinciaKm(provincia);
    }

    @Override
    public boolean addPista(SkiFondo pista) {
        return false;
    }

    @Override
    public boolean addPista(SkipAlpino pista) throws DificultadExcepcion {
        return false;
    }

    @Override
    public int consulta(String provincia) {
        return pistaDao.consulta(provincia);
    }

    @Override
    public boolean addPuebloListaPueblos(int id, String pueblo) {
        return pistaDao.addPuebloListaPueblos(id, pueblo);
    }

    @Override
    public boolean removePista(int id) {
        return pistaDao.removePista(id);
    }

    public void nuevaPistaFondo(String provincia, String nombre, String pueblo1, String pueblo2, int id){
        pistaDao.nuevaPistaFondo(provincia, nombre, pueblo1, pueblo2, id);
    }

    public void nuevaPistaAlpina(int id, String provincia, String nombre,String dificultad) throws DificultadExcepcion {
        pistaDao.nuevaPistaAlpina(id, provincia,nombre,dificultad);
    }

    @Override
    public String getPistasProvincia() {
        return pistaDao.getPistasProvincia();
    }

    @Override
    public void cargarFichero() {
        pistaDao.cargarFichero();
    }

    @Override
    public boolean escribirFichero() {
        return pistaDao.escribirFichero();
    }

    @Override
    public boolean escribirFicheroBinario() {
        return pistaDao.escribirFicheroBinario();
    }

    @Override
    public void cargarFicheroBinario() {
        pistaDao.cargarFicheroBinario();
    }
}
