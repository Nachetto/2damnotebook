package org.example.service;

import org.example.domain.CentroOcio;
import org.example.domain.ParqueAtracciones;
import java.util.List;
import java.util.Map;

public interface ServiceInterface {
    List<CentroOcio> getListaCentroOcioes();
    List<CentroOcio> listadoOrdenadoProvinciaFecha();
    boolean addCentroOcio(CentroOcio centroOcio) ;
    List<CentroOcio> consulta();
    boolean actualizarServicios(int id, String servicioNuevo);
    int removeCentrosOcio(int anyo);
    List<ParqueAtracciones> listadoOrdenado (int edad, boolean festivo);


}
