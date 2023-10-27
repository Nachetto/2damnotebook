package dao;

import common.Constantes;
import common.ExcepcionFecha;
import domain.Internacional;
import domain.Nacional;
import domain.Vuelo;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class VuelosDaoImpl implements VuelosDao {
    private List<Vuelo> vuelos;


    public VuelosDaoImpl(){
        vuelos = new ArrayList<>();
        try {
            // internacionales
            vuelos.add(new Internacional(1, "Madrid", "Londres", LocalDate.of(2021, 7, 7), (int) (100 + Math.random() * 100), 100 + Math.random() * 100, new ArrayList<>(Arrays.asList("Nueva York", "París"))));
            vuelos.add(new Internacional(2, "Barcelona", "Nueva York", LocalDate.of(2021, 8, 15), (int) (100 + Math.random() * 100), 100 + Math.random() * 100, new ArrayList<>(Arrays.asList("Londres", "Los Ángeles"))));
            vuelos.add(new Internacional(3, "Sevilla", "París", LocalDate.of(2021, 9, 20), (int) (100 + Math.random() * 100), 100 + Math.random() * 100, new ArrayList<>(Arrays.asList("Roma", "Ámsterdam"))));

            // nacionales
            vuelos.add(new Nacional(4, "Valencia", "Barcelona", LocalDate.of(2021, 10, 5), (int) (100 + Math.random() * 100), 100 + Math.random() * 100));
            vuelos.add(new Nacional(5, "Málaga", "Madrid", LocalDate.of(2021, 11, 12), (int) (100 + Math.random() * 100), 100 + Math.random() * 100));
            vuelos.add(new Nacional(6, "Bilbao", "Sevilla", LocalDate.of(2021, 12, 18), (int) (100 + Math.random() * 100), 100 + Math.random() * 100));
        } catch (ExcepcionFecha e) {
            System.out.println("Error al crear los vuelos iniciales");
        }
    }




    @Override
    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    @Override
    public boolean addVuelo(Vuelo v){
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

    public boolean nuevaEscalaPorId(int id, String escala) {
        for (Vuelo v : vuelos) {
            if (v.getId()==id) {
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
                System.out.println("Confirma si desea eliminar el siguiente vuelo " + v + "\n1. Si\n2. No: ");
                Scanner sc = new Scanner(System.in);
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1) {
                        vuelos.remove(v);
                        result = true;
                    }
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Opción no válida");
                }
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
