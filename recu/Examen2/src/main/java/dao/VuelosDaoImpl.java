package dao;

import common.Constantes;
import domain.Internacional;
import domain.Vuelo;

import java.io.*;
import java.util.*;

public class VuelosDaoImpl implements VuelosDao {
    private List<Vuelo> vuelos;

    @Override
    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    @Override
    public boolean addVuelo(Vuelo v) {
        return vuelos.add(v);
    }

    @Override
    public boolean removeVuelo(Vuelo v) {
        return vuelos.remove(v);
    }

    public List<Vuelo> vuelosPorRango(int preciominimo, int preciomaximo) {
        List<Vuelo> vuelosPorRango = new ArrayList<>();
        for (Vuelo v : vuelos) {
            if (v.getPrecio() >= preciominimo && v.getPrecio() <= preciomaximo)
                vuelosPorRango.add(v);
        }
        return vuelosPorRango;
    }

    public boolean nuevaEscalaPorId(String id, String escala) {
        for (Vuelo v : vuelos) {
            if (v.getId().equals(id)) {
                if (v instanceof Internacional)
                    return ((Internacional) v).getEscalas().add(escala);
                else {
                    System.out.println("El vuelo no es internacional");
                    return false;
                }
            }
        }
        System.out.println("No se encontro el vuelo");
        return false;
    }

    public boolean eliminarPorOrigenYDestino(String origen, String destino) {
        boolean result = false;
        for (Vuelo v : vuelos) {
            if (v.getOrigen().equalsIgnoreCase(origen) && v.getDestino().equalsIgnoreCase(destino)) {
                System.out.println("Desea eliminar el vuelo "+v+"? \n1. Si\n 2. no: ");
                Scanner sc = new Scanner(System.in);
                try{
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1) {
                        vuelos.remove(v);
                        result = true;
                    }
                } catch (NumberFormatException e) {
                    sc.nextLine();
                    System.out.println("Opcion no valida");
                }
                sc.close();
            }
        }
        return result;
    }

    public List<Vuelo> vuelosPorPrecio(){
        List<Vuelo> vuelosPorPrecio = new ArrayList<>(vuelos);
        Collections.sort(vuelosPorPrecio, new Comparator<Vuelo>() {
            @Override
            public int compare(Vuelo vuelo1, Vuelo vuelo2) {
                int comparacionPorPrecio = Double.compare(vuelo1.getPrecio(),(vuelo2.getPrecio()));
                if (comparacionPorPrecio == 0) {
                    return Integer.compare(vuelo1.getPasajerosMaximos(), vuelo2.getPasajerosMaximos());
                }
                return comparacionPorPrecio;
            }
        });
        return vuelosPorPrecio;
    }

    public boolean escribirTXT(){
        try {
            FileWriter fichero = new FileWriter("src//main//resources//FicheroTXT");
            for (Vuelo vuelo : vuelos) {
                fichero.write(vuelo.toString());
            }
            fichero.close();
            return true;
        } catch (IOException e) {
            System.out.println(Constantes.ERRORFICHERO+" porque:"+e.getMessage());
            return false;
        }
    }
    public boolean escribirBinario(){
        try {
            FileOutputStream archivoBinario = new
                    FileOutputStream("src//main//resources//FicheroBIN");
            ObjectOutputStream escribir = new
                    ObjectOutputStream(archivoBinario);
            escribir.writeObject(vuelos);//un arraylist
            archivoBinario.close();
            escribir.close();
            return true;
        } catch (IOException e) {
            System.out.println(Constantes.ERRORBINARIO+" porque:"+e.getMessage());
            return false;
        }
    }

    public boolean cargarBinario(){
        try {
            FileInputStream archivoBinario = new
                    FileInputStream("src//main//resources//FicheroBIN");
            ObjectInputStream leer = new
                    ObjectInputStream(archivoBinario);
            vuelos = (ArrayList<Vuelo>) leer.readObject();
            archivoBinario.close();
            leer.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(Constantes.ERRORBINARIO+" porque:"+e.getMessage());
            return false;
        }
    }
}
