package service;

import dao.PistasDao;
import dao.PistasDaoImpl;
import domain.Pista;
import domain.SkiAlpino;
import domain.SkiFondo;

import java.util.List;

public class Service{
    private final PistasDaoImpl dao;
    public Service(){
        this.dao=new PistasDaoImpl();
    }

    public List<Pista> getPistas() {
        return dao.getPistas();
    }

    public boolean addPista(Pista p) {
        return dao.addPista(p);
    }

    public int kmExtensionPorProvincia(String provincia) {
        return dao.kmExtensionPorProvincia(provincia);
    }

    public boolean nuevoPuebloParaPista(String nombreDeLaPista, String nuevoPueblo) {
        return dao.nuevoPuebloParaPista(nombreDeLaPista, nuevoPueblo);
    }

    public List<Pista> obtenerPistasOrdenadasPorProvinciaYKm() {
        return dao.obtenerPistasOrdenadasPorProvinciaYKm();
    }

    public boolean removePista(Pista p) {
        return dao.removePista(p);
    }

    public boolean eliminarPistaPorId(int idPista) {
        return dao.eliminarPistaPorId(idPista);
    }

    public boolean escribirFichero() {
        return dao.escribirFichero();
    }

    public boolean escribirBinario() {
        return dao.escribirBinario();
    }

    public boolean cargarBinario() {
        return dao.cargarBinario();
    }
}
