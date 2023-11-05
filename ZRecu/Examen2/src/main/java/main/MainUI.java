package main;

import common.Constantes;
import common.ExcepcionFecha;
import domain.Internacional;
import domain.Nacional;
import service.VuelosService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainUI {
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.menu();
    }

    private void menu() {
        VuelosService service = new VuelosService();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 11) {
            opcion = 0;
            System.out.println(Constantes.MENUINICIO);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println(Constantes.ERRORMENU);
            }
            switch (opcion) {
                case 1 -> System.out.println(service.getVuelos());
                case 2 -> System.out.println(service.vuelosPorPrecio());
                case 3 -> {
                    //3. Añadir un nuevo vuelo
                    System.out.println("Introduce el tipo de vuelo");
                    String tipo = sc.nextLine();
                    if (tipo.equalsIgnoreCase("nacional"))
                        try{
                            System.out.println("Introduce el origen");
                            String origen = sc.nextLine();
                            System.out.println("Introduce el destino");
                            String destino = sc.nextLine();
                            System.out.println("Introduce el precio");
                            double precio = sc.nextDouble();
                            System.out.println("Introduce el numero de pasajeros maximos");
                            int pasajerosMaximos = sc.nextInt();
                            System.out.println("Introduce el dia");
                            int dia = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el mes");
                            int mes = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el año");
                            int anyo = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el id");
                            int id = sc.nextInt();
                            sc.nextLine();
                            //String id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio
                            LocalDate fecha = LocalDate.of(anyo,mes,dia);
                            Nacional vuelo = new Nacional(id, origen, destino, fecha, pasajerosMaximos, precio);
                            if (service.addVuelo(vuelo))
                                System.out.println("Vuelo añadido correctamente");
                            else
                                System.out.println("No se ha podido añadir el vuelo");
                        }catch (Exception e) {
                            System.out.println("Error al introducir los datos: " +e.getMessage());
                        }
                    else if (tipo.equalsIgnoreCase("internacional")) {
                        try {
                            //int id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio, List<String> escalas
                            System.out.println("Introduce el id");
                            int id = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el origen");
                            String origen = sc.nextLine();
                            System.out.println("Introduce el destino");
                            String destino = sc.nextLine();
                            System.out.println("Introduce el dia");
                            int dia = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el mes");
                            int mes = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el año");
                            int anyo = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce los pasajeros maximos");
                            int pasajerosMaximos = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Introduce el precio");
                            double precio = sc.nextDouble();
                            sc.nextLine();
                            System.out.println("Introduce las escalas separadas por espacios");
                            List<String> escalas = Arrays.asList(sc.nextLine().split(" "));
                            Internacional vuelo = new Internacional(id, origen, destino, LocalDate.of(anyo,mes,dia), pasajerosMaximos, precio, escalas);
                            if (service.addVuelo(vuelo))
                                System.out.println("Vuelo añadido correctamente");
                            else
                                System.out.println("No se ha podido añadir el vuelo");
                        }catch (Exception e) {
                            System.out.println("Error al introducir los datos" +e.getMessage());
                        }
                    }

                }
                case 4 -> {
                    //4. Añadir una escala a un vuelo internacional especifico
                    System.out.println("Introduce el id del vuelo");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Introduce la escala");
                    String escala = sc.nextLine();
                    if (service.nuevaEscalaPorId(id, escala))
                        System.out.println("Escala añadida correctamente");
                    else
                        System.out.println("No se ha podido añadir la escala");
                }
                case 5 -> {
                    //5. Eliminar vuelos por origen y destino indicados
                    System.out.println("Introduce el origen");
                    String origen = sc.nextLine();
                    System.out.println("Introduce el destino");
                    String destino = sc.nextLine();
                    if (service.eliminarPorOrigenYDestino(origen, destino))
                        System.out.println("Vuelo eliminado correctamente");
                    else
                        System.out.println("No se ha podido eliminar el vuelo");
                }
                case 6 -> {
                    //6. Pasar a un fichero de texto todos los vuelos
                    if (service.escribirTXT())
                        System.out.println("Vuelos escritos correctamente");
                    else
                        System.out.println("No se ha podido escribir los vuelos");
                }
                case 7 -> {
                    //7. Pasar a un binario todos los vuelos
                    if (service.escribirBinario())
                        System.out.println("Vuelos escritos correctamente");
                    else
                        System.out.println("No se ha podido escribir los vuelos");
                }
                case 8 -> {
                    //8. Cargar todos los vuelos desde el binario
                    if (service.cargarBinario())
                        System.out.println("Vuelos cargados correctamente");
                    else
                        System.out.println("No se ha podido leer los vuelos");
                }
                case 9 -> {
                    //9. Cargar todos los vuelos desde el fichero de texto
                }
                case 10 -> {
                    //10. Mapa que para cada origen la lista de vuelos que salen desde alli
                }
                case 11 -> System.out.println("Adios");
            }
        }
        sc.close();
    }
}
