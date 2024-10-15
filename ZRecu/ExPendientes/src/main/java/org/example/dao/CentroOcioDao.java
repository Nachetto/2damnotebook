package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.PrecioException;
import org.example.common.ValoracionException;
import org.example.domain.CentroOcio;
import org.example.domain.ParqueAtracciones;
import org.example.domain.Zoologico;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CentroOcioDao {
    private List<CentroOcio> centros;


    public CentroOcioDao() {
        this.centros = new ArrayList<>();
        //creando servicios del zoologico
        List<String> servicios1 = new ArrayList<>(Arrays.asList("delfinatorio", "pingüinos", "exhibición", "Aves"));
        List<String> servicios2 = new ArrayList<>(Arrays.asList("delfinatorio", "pingüinos", "exhibición", "Aves"));
        List<String> servicios3 = new ArrayList<>(Arrays.asList("delfinatorio", "pingüinos", "exhibición", "Aves"));
        List<String> servicios4 = new ArrayList<>(Arrays.asList("delfinatorio", "pingüinos", "exhibición", "Aves"));

        try {
            //ParqueAtracciones
            centros.add(new ParqueAtracciones(1, "parque1", (int) (Math.random() * 10) + 16, "provincia1", fechaAleatoria(), "correo1@gmail.com", (int) (Math.random() * 5) + 1, 0));
            centros.add(new ParqueAtracciones(2, "parque2", (int) (Math.random() * 10) + 16, "provincia2", fechaAleatoria(), "correo2@gmail.com", (int) (Math.random() * 5) + 1, 0));
            centros.add(new ParqueAtracciones(3, "parque3", (int) (Math.random() * 10) + 16, "provincia1", fechaAleatoria(), "correo3@gmail.com", (int) (Math.random() * 5) + 1, 0));
            centros.add(new ParqueAtracciones(4, "parque4", (int) (Math.random() * 10) + 16, "provincia2", fechaAleatoria(), "correo4@gmail.com", (int) (Math.random() * 5) + 1, 0));
            //Zoologicos
            centros.add(new Zoologico(5, "parque5", (int) (Math.random() * 10) + 11, "provincia1", fechaAleatoria(), "correo5@gmail.com", (int) (Math.random() * 5) + 1, servicios1));
            centros.add(new Zoologico(6, "parque6", (int) (Math.random() * 10) + 11, "provincia2", fechaAleatoria(), "correo6@gmail.com", (int) (Math.random() * 5) + 1, servicios2));
            centros.add(new Zoologico(7, "parque7", (int) (Math.random() * 10) + 11, "provincia1", fechaAleatoria(), "correo7@gmail.com", (int) (Math.random() * 5) + 1, servicios3));
            centros.add(new Zoologico(8, "parque8", (int) (Math.random() * 10) + 11, "provincia2", fechaAleatoria(), "correo8@gmail.com", (int) (Math.random() * 5) + 1, servicios4));
        } catch (Exception e) {
            System.out.println("Error al crear los centros de ocio iniciales: " + e.getMessage());
        }
    }

    private LocalDate fechaAleatoria() {
        return LocalDate.of(
                (int) (Math.random() * 2023) + 1,
                (int) (Math.random() * 12) + 1,
                (int) (Math.random() * 28) + 1//evitamos problemas
        );
    }


    //EJERCICIO 1.- Listado de todos los centros de ocio
    public List<CentroOcio> getCentros() {
        return centros;
    }


    //EJERCICIO 2.- Listado ordenado de centros por provincia y a igualdad de provincia por fecha de construccion
    public List<CentroOcio> listadoOrdenadoProvinciaFecha() {
        List<CentroOcio> copialista = getCentros();
        copialista.sort(Comparator.comparing(CentroOcio::getProvincia)
                .thenComparing(CentroOcio::getFechaConstruccion));
        return copialista;
    }


    //Ejercicio 3.- Añadir un Centro nuevo
    public boolean addCentro(CentroOcio c) {
        return centros.add(c);
    }


    //Ejercicio 4 EN EL MAIN PORQUE NO VOY A HACER UN SOUT AQUI


    //Ejercicio 5.- Añadir un servicio a la lista de servicios del id del zoologico proporcionado
    public boolean newService(int id, String servicioNuevo) {
        //considero que es mejor interar con un for que con un foreach
        for (CentroOcio centro : centros) {
            if (centro instanceof Zoologico && centro.getId() == id) {
                ((Zoologico) centro).getServicios().add(servicioNuevo);
                return true;
            }
        }
        return false;
    }


    //Ejercicio 6.- Devolver una coleccion de los parques de atracciones ordenados por provincia y a igualdad de
    // provincia por precio de entrada para una edad introducido por el usuario y si es festivo o no introducido por el usuario
    public List<ParqueAtracciones> listadoOrdenadoParqueAtracciones(int edad, boolean festivo) {

        List<ParqueAtracciones> parquesCasteados = sacarParquesDeAtracciones();

        parquesCasteados.sort(Comparator.comparing(ParqueAtracciones::getProvincia)
                .thenComparing(parque -> parque.precioEntrada(edad, festivo)));

        return parquesCasteados;
    }

    private List<ParqueAtracciones> sacarParquesDeAtracciones() {
        //saco los parques de atracciones de la lista tocha
        return getCentros().stream()
                .filter(centro -> centro instanceof ParqueAtracciones)
                .map(centro -> (ParqueAtracciones) centro)
                .collect(Collectors.toList());
    }


    //Ejercicio 7.- Eliminar todos los centros de ocio que se hayan construido antes de un año determinado devolviendo
    //el numero de centros eliminados
    public int removeCentrosOcio(int anyo) {
        int sizeBefore = centros.size();
        centros.removeIf(centroOcio -> centroOcio.getFechaConstruccion().getYear() < anyo);
        int sizeAfter = centros.size();
        return sizeBefore - sizeAfter;
    }


    //Ejercicio 8.- volcar toda la informacion a un fichero de texto
    public boolean guardarTXT() {
        try (FileWriter writer = new FileWriter("FicheroDeTexto")) {
            //centros.forEach(centro-> writer.write(centro.toString()+"\n"));
            for (CentroOcio centro : centros) {
                writer.write(centro.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            System.out.println("ERROR ESCRITURA ESPECIFICO:\n " + e.getMessage());
            return false;
        }
    }


    //Ejercicio 9.- volcar toda la informacion a un fichero binario
    public boolean escribirBinario() {
        try (FileOutputStream archivoBinario = new FileOutputStream("FicheroBinario");
             ObjectOutputStream escribir = new ObjectOutputStream(archivoBinario)) {
            escribir.writeObject(centros);
            return true;
        } catch (IOException e) {
            System.out.println("ERROR ESCRITURA ESPECIFICO:\n " + e.getMessage());
            return false;
        }
    }


    //Ejercicio 10.- Pasar todos los parques de atracciones a un fichero de JSON
    public boolean escribirJSON() {
        try (FileWriter writer = new FileWriter("parques.json")) {
            List<ParqueAtracciones> parques = sacarParquesDeAtracciones();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//para que lea la fecha
                    .setPrettyPrinting().create();
            String json = gson.toJson(parques);

            writer.write(json);
            return true;
        } catch (IOException e) {
            System.out.println("ERROR ESCRITURA ESPECIFICO:\n " + e.getMessage());
            return false;
        }
    }


    //Ejercicio 11.- Mostrar la informacion del fichero JSON que se ha creado en el ejercicio anterior
    public ParqueAtracciones[] leerJSON() {
        ParqueAtracciones[] parques = null;
        try (FileReader reader = new FileReader("parques.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            parques = gson.fromJson(reader, ParqueAtracciones[].class);
        } catch (IOException e) {
            System.out.println("ERROR LECTURA ESPECIFICO:\n " + e.getMessage());
        }
        return parques;
    }


    //Ejercicio 12.- Leer la informacion de un fichero binario y cargarla en la lista de centros de ocio
    public boolean leerBinario() {
        try (FileInputStream archivoBinario = new FileInputStream("FicheroBinario");
             ObjectInputStream leer = new ObjectInputStream(archivoBinario)) {
            centros = (List<CentroOcio>) leer.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR LECTURA ESPECIFICO:\n " + e.getMessage());
            return false;
        }
    }

    //Ejercicio 13.- Leer la informacion de un fichero de texto y cargarla en la lista de centros de ocio
    public boolean cargarFicheroTexto() {
        boolean resultado = true;
        try (FileReader fichero = new FileReader("FicheroDeTexto");
             BufferedReader lector = new BufferedReader(fichero)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos[0].equals("ParqueAtracciones")) {
                    try {
                        centros.add(new ParqueAtracciones(Integer.parseInt(datos[1]), datos[2], Integer.parseInt(datos[3]), datos[4], LocalDate.parse(datos[5]), datos[6], Integer.parseInt(datos[7]), Integer.parseInt(datos[8])));
                        resultado = true;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        resultado = false;
                    }
                } else if (datos[0].equals("Zoologico")) {
                    List<String> servicios = Arrays.stream(datos[7].split(","))
                            .filter(s -> !s.isEmpty())//la coma del final del string me crea un string vacio, asi que lo quito
                            .toList();
                    centros.add(new Zoologico(Integer.parseInt(datos[1]), datos[2], Integer.parseInt(datos[3]), datos[4], LocalDate.parse(datos[5]), datos[6], Integer.parseInt(datos[7]), servicios));
                    resultado = true;
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR LECTURA ESPECIFICO:\n " + e.getMessage());
            resultado = false;
        } catch (ValoracionException e) {
            //es posible que editen el archivo y pongan una valoracion incorrecto, pero para el examen no lo voy a tener en cuenta
            resultado = false;
        } catch (PrecioException e) {
            //es posible que editen el archivo y pongan un precio incorrecto, pero para el examen no lo voy a tener en cuenta
            resultado = false;
        }
        return resultado;
    }


    //Ejercicio 14.- Método que devuelva un mapa que contenga para cada provincia, la lista de centros de ocio que existen.
    //Verificar la salida de dicho Map y aprovechar para obtener el número de zoos y el de parques de atracciones de cada provincia.
    public Map<String, List<CentroOcio>> mapa() {
        return centros.stream()
                .collect(Collectors.groupingBy
                        (CentroOcio::getProvincia));
    }

}
