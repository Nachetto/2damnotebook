package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.common.Constantes;
import org.example.common.FechaException;
import org.example.domain.Internacional;
import org.example.domain.Nacional;
import org.example.domain.Vuelo;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VueloDaoImpl implements VueloDao {
    List<Vuelo> vuelos;
    public VueloDaoImpl(){
        this.vuelos=new ArrayList<>();
        try {
            vuelos.add(new Nacional(1,50,50,"Ryanair", "madrid","barcelona", LocalDate.of(2023,10,28)));
            vuelos.add(new Nacional(2,51,51,"Ryanair", "madrid","barcelona", LocalDate.of(2024,10,28)));
            vuelos.add(new Nacional(3,52,52,"Ryanair", "barcelona","madrid", LocalDate.of(2025,10,28)));
            vuelos.add(new Nacional(4,53,53,"Ryanair", "barcelona","madrid", LocalDate.of(2026,10,28)));
            vuelos.add(new Internacional(5,55,55,"Iberia","valencia","dublin",LocalDate.of(2027,10,28)));
            vuelos.add(new Internacional(6,56,56,"Iberia","valencia","miami",LocalDate.of(2028,10,28)));
            vuelos.add(new Internacional(7,57,57,"Iberia","bilbao","paris",LocalDate.of(2029,10,28)));
            vuelos.add(new Internacional(8,58,58,"Iberia","bilbao","lisboa",LocalDate.of(2030,10,28)));
        }catch (FechaException e){
            System.out.println("No se ha podido crear el vuelo, razon: "+e.getMessage());
        }
    }

    //1.- Devuelve la lista de los vuelos
    @Override
    public List<Vuelo> getListaVuelos() {
        return vuelos;
    }

    //2.- Devuelve los vuelos que ha especificado por origen, destino y precios.
    @Override
    public List<Vuelo> consulta(String origen, String destino, double precio1, double precio2) {
        return vuelos.stream().filter(vuelo -> vuelo.getOrigen().equalsIgnoreCase(origen) && vuelo.getDestino().equalsIgnoreCase(destino)
                && vuelo.getPrecioavg()>precio1 && vuelo.getPrecioavg()<precio2).collect(Collectors.toList());
    }

    //3.- Añade un vuelo
    @Override
    public boolean addVuelo(Vuelo vuelo) throws FechaException {
        return vuelos.add(vuelo);
        //la verificacion de la fecha se encuentra en el constructor del vuelo
    }
    public Vuelo filtrandoNuevoVuelo(String tipo, int id, int maxpasajeros, double precioavg, String aereolinea, String origen,  String destino,  LocalDate fecha, List<String> nuevaescala){
        Vuelo nuevovuelo=null;
        try{
            if (tipo.equalsIgnoreCase("internacional")){
                nuevovuelo =new Internacional(id, maxpasajeros, precioavg, aereolinea, origen, destino, fecha);
                ((Internacional)nuevovuelo).setEscala(nuevaescala);
            }
            else
                nuevovuelo= new Nacional(id, maxpasajeros, precioavg, aereolinea, origen, destino, fecha);
        }catch (FechaException e) {
            System.out.println("No se ha podido crear el vuelo, razon: " + e.getMessage());
        }
        return nuevovuelo;
    }

    //4.- añadir escala a un vuelo internacional identificado por su id
    @Override
    public boolean addEscala(int id, String pais) {
        boolean respuesta = false;
        for (Vuelo v:vuelos) {
            if (v.getId()==id)
                respuesta= ((Internacional)v).nuevaEscala(pais);
        }
        return respuesta;
    }

    //5.- Eliminar vuelos con origen y destino especificados preguntando por confirmacion
    @Override
    public boolean removeVuelo(String origen, String destino) {
        boolean respuesta = false;
        for (Vuelo v:vuelos) {
            if (v.getOrigen().equalsIgnoreCase(origen)&&v.getDestino().equalsIgnoreCase(destino)){
                Scanner sc = new Scanner(System.in);
                System.out.println(Constantes.CONFIRMAR_REMOVEVUELO+v);
                String answer = sc.next();
                sc.nextLine();
                while (!answer.equalsIgnoreCase("Si")&&!answer.equalsIgnoreCase("No")){
                    System.out.println(Constantes.ERROR_REMOVEVUELO);
                    answer = sc.next();
                    sc.nextLine();
                }
                if (answer.equalsIgnoreCase("si")){
                     vuelos.remove(v);
                     respuesta=true;}
                sc.close();
            }
        }
        return respuesta;
    }


    //6.- Escribir TXT
    @Override
    public boolean escribirFichero() {
        boolean respuesta = true;
        try (PrintWriter writer = new PrintWriter(new FileWriter("FicheroTXT"))) {
            for (Vuelo v : vuelos) {
                    writer.println(v.toString());
                }
            } catch (IOException e) {
                respuesta = false;
                System.out.println("Error al guardar el usuario en el archivo: "+ e.getMessage());
            }
        return respuesta;
    }

    //7.- Escribir Binario
    @Override
    public boolean escribirFicheroBinario() {
            try {
                FileOutputStream archivoBinario = new FileOutputStream("FicheroBIN.bin");
                ObjectOutputStream escribir = new ObjectOutputStream(archivoBinario);
                escribir.writeObject(vuelos);
                archivoBinario.close();
                escribir.close();
                return true;
            } catch (IOException e) {
                System.out.println("Ha habido un error al guardar el archivo binario.");
                return false;
            }
    }

    //8.- Cargar Binario
    @Override
    public List<Vuelo> cargarFicheroBinario() {
        ArrayList<Vuelo> vuelosCargados= new ArrayList<>();
        try {
            FileInputStream archivoEntrada = new FileInputStream("FicheroBIN.bin");
            ObjectInputStream lectorObjeto = new ObjectInputStream(archivoEntrada);
            vuelosCargados = (ArrayList<Vuelo>) lectorObjeto.readObject();
            lectorObjeto.close();
        } catch (IOException e) {
            System.out.println("Ha habido un error al cargar el archivo  binario: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer el objeto del archivo binario: " + e.getMessage());
        }
        return vuelosCargados;
    }
    public void guardarListaDesdeBinarioGuardado(List<Vuelo> lista){
        vuelos=lista;
        System.out.println("Se han actualizado a ram los vuelos, ahora tienes los siguientes: "+vuelos);
    }

    //9.- Cargar txt
    @Override
    public List<Vuelo> cargarFichero() {
        Scanner scanner = null;
        vuelos = new ArrayList<>();
        try {
            scanner = new Scanner(new File("FicheroTXT"));
            while (scanner.hasNextLine()) {
                String[] resultado = scanner.nextLine().split(";");
                if (resultado[0].equalsIgnoreCase("Internacional")) {
                    //2023,06,12
                    int anyo=Integer.parseInt(resultado[7].substring(1,4));
                    int mes=Integer.parseInt(resultado[7].substring(6,7));
                    int dia=Integer.parseInt(resultado[7].substring(9,10));
                    //{escala1,escala2,escala3,escala4}
                    String escalasraw=resultado[8];
                    escalasraw = escalasraw.trim();// Elimina los espacios
                    List<String> listaescalas = new ArrayList<String>();
                    escalasraw= escalasraw.substring(1,resultado[8].length()-1);
                    String[] escalasarray = escalasraw.split(",");
                    for (String escala : escalasarray) {
                        listaescalas.add(escala);
                    }
                    //int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha
                    Vuelo nuevovuelo = new Internacional(Integer.parseInt(resultado[1]),Integer.parseInt(resultado[2]),Integer.parseInt(resultado[3]),
                            resultado[4], resultado[5], resultado[6], LocalDate.of(anyo,mes,dia));
                    ((Internacional)nuevovuelo).setEscala(listaescalas);
                    vuelos.add(nuevovuelo);
                }else if (resultado[0].equalsIgnoreCase("Nacional")) {
                    //2023,06,12
                    int anyo=Integer.parseInt(resultado[7].substring(1,4));
                    int mes=Integer.parseInt(resultado[7].substring(6,7));
                    int dia=Integer.parseInt(resultado[7].substring(9,10));
                    //int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha
                    vuelos.add(new Internacional(Integer.parseInt(resultado[1]),Integer.parseInt(resultado[2]),Integer.parseInt(resultado[3]),
                            resultado[4], resultado[5], resultado[6], LocalDate.of(anyo,mes,dia)));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (FechaException e) {
            System.out.println("No se ha podido crear el vuelo, razon: " + e.getMessage());
        }
        return vuelos;
    }
    public void guardarListaDesdeTextoGuardado(List<Vuelo> lista){
        vuelos=lista;
        System.out.println("Se han actualizado a ram los vuelos, ahora tienes los siguientes: "+vuelos);
    }

    //10
    @Override
    public List<Vuelo> listadoOrdenadoPrecioPasajeros(String origen, String destino) {
        return null;
    }

    //11
    @Override
    public Map<String, List<Vuelo>> getVuelosOrigen() {
        return null;
    }

    //12
    //13
    public List<Vuelo> cargarJSON(){
        BufferedReader bufferedReader = null;
        List<Vuelo >vuelosnuevos = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new
                    FileReader("FicheroJSON.json"));
            Gson gson = new Gson();
            Type listType = TypeToken.getParameterized(List.class,Vuelo.class).getType();
            List<Vuelo> pistaList = gson.fromJson(bufferedReader,listType);
            vuelosnuevos.addAll(pistaList);
            System.out.println("Todo correcto");
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vuelosnuevos;
    }
    public void guardarListaDesdeJSONGuardado(List<Vuelo> lista){
        vuelos=lista;
        System.out.println("Se han actualizado a ram los vuelos, ahora tienes los siguientes: "+vuelos);
    }
}
