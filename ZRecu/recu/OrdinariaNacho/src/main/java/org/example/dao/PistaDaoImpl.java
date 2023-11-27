package org.example.dao;

import org.example.common.ComparacionPorProvincia;
import org.example.common.Constantes;
import org.example.common.DificultadExcepcion;
import org.example.common.VerificarDificultadExcepcion;
import org.example.domain.Pista;
import org.example.domain.SkiFondo;
import org.example.domain.SkipAlpino;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PistaDaoImpl implements PistaDao {
    List<Pista> pistas = null;

    public PistaDaoImpl() {
        pistas = new ArrayList<>();
    }

    public void agregarValoresIniciales() {
        try {
            pistas.add(new SkipAlpino(1, (int) Math.floor(Math.random() * 5) + 1, "Madrid", "Aliga", "Roja"));// al ser
                                                                                                              // ski de
                                                                                                              // Alpino
                                                                                                              // hay
                                                                                                              // menos
                                                                                                              // kilómetros
                                                                                                              // que en
                                                                                                              // el Ski
                                                                                                              // Fondo
            pistas.add(new SkipAlpino(2, (int) Math.floor(Math.random() * 5) + 1, "Barcelona", "Pinflon", "verde"));
            pistas.add(new SkipAlpino(3, (int) Math.floor(Math.random() * 5) + 1, "Alicante", "Casteo", "Azul"));
            pistas.add(new SkipAlpino(4, (int) Math.floor(Math.random() * 5) + 1, "Valencia", "Fallaski", "verde"));
        } catch (DificultadExcepcion e) {
            System.out.println(
                    "Es literalmente imposible que esta excepción salte porque he sido yo el que introduce los datos y no el usuario pero"
                            +
                            " mejor hago el try catch aqui que no cuando vaya a construir pistadaoimpl");
        }
        pistas.add(new SkiFondo(5, (int) Math.floor(Math.random() * 10) + 1, "Sevilla", "Colorespecial", "lagunas",
                "algete"));// al ser ski de Fondo hay mas kilómetros que en el Ski Alpino
        pistas.add(
                new SkiFondo(6, (int) Math.floor(Math.random() * 10) + 1, "PaisVasco", "Euskera", "mungia", "gatica"));
        pistas.add(new SkiFondo(7, (int) Math.floor(Math.random() * 10) + 1, "Asturias", "Gaitas", "sidra", "llanes"));
        pistas.add(
                new SkiFondo(8, (int) Math.floor(Math.random() * 10) + 1, "Galicia", "cocacola", "oporto", "fedora"));
    }

    @Override
    public List<Pista> getListaPistas() {
        return pistas;
    }

    @Override
    public List<Pista> listadoOrdenadoProvinciaKm(String provincia) {
        return pistas.stream()
                .filter(pista -> getPistasProvincia().equals(provincia))
                .sorted(new ComparacionPorProvincia())
                .collect(Collectors.toList());
    }


    

    @Override
    public boolean addPista(SkipAlpino pista) throws DificultadExcepcion {
        boolean resultado = false;
        String
        VerificarDificultadExcepcion.verificandoDificultadExcepcion(pista.getDificultad());
        pistas.add(pista);
        resultado = true;
        return resultado;
    }

    @Override
    public boolean addPista(SkiFondo pista) {
        pistas.add(pista);
        return true;
    }

    public void nuevaPistaFondo(String provincia, String nombre, String pueblo1, String pueblo2, int id) {
        Pista nueva = new SkiFondo(id, (int) Math.floor(Math.random() * 10) + 1, provincia, nombre, pueblo1, pueblo2);
        if (addPista((SkiFondo) nueva))
            System.out.println(Constantes.SKIFONDOOK);
        else
            System.out.println(Constantes.SKIFONDOMAL);
    }

    public void nuevaPistaAlpina(int id, String provincia, String nombre, String dificultad)
            throws DificultadExcepcion {
        Pista nueva = new SkipAlpino(id, (int) Math.floor(Math.random() * 5) + 1, provincia, nombre, dificultad);
        if (addPista((SkipAlpino) nueva))
            System.out.println(Constantes.SKIFONDOOK);
        else
            System.out.println(Constantes.SKIFONDOMAL);
    }

    @Override
    public int consulta(String provincia) {
        double result = pistas.stream()
                .filter(pista -> getPistasProvincia().contains(provincia))
                .mapToDouble(Pista::getKm).sum();
        return (int) result;
    }

    @Override
    public boolean addPuebloListaPueblos(int id, String pueblo) {
        boolean respuesta = false;
        SkiFondo s = (SkiFondo) pistas.get(id);
        List<String> cambiada = s.getPueblos();
        cambiada.add(pueblo);
        s.setPueblos(cambiada);
        pistas.remove(id);
        pistas.add(s);
        respuesta = true;
        return respuesta;
    }

    @Override
    public boolean removePista(int id) {
        boolean respuesta = false;
        try {
            pistas.remove(id);
            respuesta = true;
        } catch (NullPointerException e) {
            System.out.println("No exise una pista con ese identificador");
        }
        return respuesta;
    }

    @Override
    public String getPistasProvincia() {
        return "Madrid";
    }

    @Override
    public boolean escribirFichero() {
        boolean respuesta = true;
        try (PrintWriter writer = new PrintWriter(new FileWriter("FicheroTXT"))) {
            for (Pista pista : pistas) {
                writer.println(pista.toString());
            }
        } catch (IOException e) {
            respuesta = false;
            System.out.println("Error al guardar el usuario en el archivo: "
                    + e.getMessage());
        }
        return respuesta;
    }

    @Override
    public boolean escribirFicheroBinario() {
        try {
            FileOutputStream archivoBinario = new FileOutputStream("FicheroBIN");
            ObjectOutputStream escribir = new ObjectOutputStream(archivoBinario);
            escribir.writeObject(pistas);// un arraylist
            archivoBinario.close();
            escribir.close();
            System.out.println("Todo guardado.");
            return true;
        } catch (IOException e) {
            System.out.println("Ha habido un error al guardar el archivo binario.");
            return false;
        }
    }

    @Override
    public void cargarFichero() {
        Scanner scanner = null;
        pistas = new ArrayList<>();
        try {
            scanner = new Scanner(new File("FicheroTXT"));
            while (scanner.hasNextLine()) {
                String[] resultado = scanner.nextLine().split(";");
                if (resultado[0].equalsIgnoreCase("skialpino")) {
                    int id = Integer.parseInt(resultado[1]),km = Integer.parseInt(resultado[2]);
                    String provincia = resultado[3], nombre = resultado[4], dificultad = resultado[5];
                    pistas.add(new SkipAlpino(id, km, provincia, nombre, dificultad));
                } else if(resultado[0].equalsIgnoreCase("skifondo")){
                    // Extrae la parte correspondiente a los pueblos
                    int pueblosStartIndex = resultado[5].indexOf("[") + 1; // Índice de inicio de los pueblos
                    int pueblosEndIndex = resultado[5].lastIndexOf("]") - 1; // Índice de fin de los pueblos (excluye "]")
                    String pueblosText = resultado[5].substring(pueblosStartIndex, pueblosEndIndex);
                    pueblosText = pueblosText.trim();// Elimina los espacios adicionales
                    List<String> pueblos = new ArrayList<String>();
                    String[] pueblosarray= pueblosText.split(",");
                    for (String pueblo : pueblosarray) {
                        pueblos.add(pueblo);
                    }
                    //SkiFondo(int id, int km, String provincia, String nombre, String pueblo1, String pueblo2)
                    pistas.add(new SkiFondo(Integer.parseInt(resultado[1]), Integer.parseInt(resultado[2]), resultado[3], resultado[4], pueblos));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (DificultadExcepcion e) {
            System.out.println("Hey, no puedes crear una pista porque la dificultad no existe");
        }
    }
    public void escribirJSON() {
        Pista pista = new Pista(0, 0, null, null);
        String json = pista.toJson();
        try (FileWriter writer = new FileWriter("data.json",true)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarJSON() {
        BufferedReader bufferedReader = null;
        pistas = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("FicheroJSON.json"));
            Gson gson = new Gson();
            Type listType = TypeToken.getParameterized(List.class, Pista.class).getType();
            List<Pista> pistaList = gson.fromJson(bufferedReader, listType);
            pistas.addAll(pistaList);
            
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void cargarFicheroBinario() {
        try {
            // Abrir el archivo para leer
            FileInputStream archivoEntrada = new FileInputStream("archivo.bin");
            ObjectInputStream lectorObjeto = new ObjectInputStream(archivoEntrada);
            // Leer el objeto del archivo
            pistas = (ArrayList<Pista>) lectorObjeto.readObject();
            // Cerrar el archivo de lectura
            lectorObjeto.close();
        } catch (IOException e) {
            System.out.println("Ha habido un error al cargar el archivo binario: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer el objeto del archivo binario: " + e.getMessage());
        }
    }
    
}