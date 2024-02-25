package org.example.ui;

import org.example.common.Constantes;
import org.example.domain.CentroOcio;
import org.example.domain.ParqueAtracciones;
import org.example.domain.Zoologico;
import org.example.service.Service;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private final Service sv = new Service();

    public void run() {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 15) {
            try{
                System.out.println(Constantes.QUIERE_VER_DEL_1_AL_14_15_PARA_SALIR);
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        ejercicio1();
                        break;
                    case 2:
                        ejercicio2();
                        break;
                    case 3:
                        ejercicio3(sc);
                        break;
                    case 4:
                        ejercicio4();
                        break;
                    case 5:
                        ejercicio5(sc);
                        break;
                    case 6:
                        ejercicio6(sc);
                        break;
                    case 7:
                        ejercicio7(sc);
                        break;
                    case 8:
                        ejercicio8();
                        break;
                    case 9:
                        ejercicio9();
                        break;
                    case 10:
                        ejercicio10();
                        break;
                    case 11:
                        ejercicio11();
                        break;
                    case 12:
                        ejercicio12();
                        break;
                    case 13:
                        ejercicio13();
                        break;
                    case 14:
                        Map<String, List<CentroOcio>> mapa = sv.mapa();
                        for (Map.Entry<String, List<CentroOcio>> entry : mapa.entrySet()) {
                            System.out.printf("Provincia: %s, Número de centros de ocio: %d%n", entry.getKey(), entry.getValue().size());
                        }
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Has introducido un dato invalido, Vuelvo a sacar el menu.");
                sc.next();
                option = 0;
            } catch (Exception e) {
                System.out.println(Constantes.ERROR_VUELVO_A_SACAR_EL_MENU);
                option = 0;
            }
        }
        System.out.println(Constantes.HASTA_LUEGO);

    }


    private void ejercicio1() {
        System.out.println(sv.getListaCentroOcioes().toString().replace(", ", "\n").replace("[", "").replace("]", ""));//esto es para que quede bonito
    }


    private void ejercicio2() {
        System.out.println(sv.listadoOrdenadoProvinciaFecha().toString().replace(", ", "\n").replace("[", "").replace("]", ""));//esto es para que quede bonito);
    }


    private void ejercicio3(Scanner sc) {
        int opcionNueva = 0;
        while (opcionNueva != 3) {
            System.out.println(Constantes.DESEA_SALIR_PULSE_3);
            try {
                opcionNueva = sc.nextInt();
                sc.nextLine();

                int id;
                String nombre;
                int precioEntrada;
                String provincia;
                LocalDate fechaConstruccion = null;
                String correo;
                int valoracion;

                System.out.println("Introduce el ID: ");
                id = sc.nextInt();
                sc.nextLine();

                System.out.println("Introduce el nombre: ");
                nombre = sc.nextLine();

                System.out.println("Introduce el Precio de la Entrada: ");
                precioEntrada = sc.nextInt();
                sc.nextLine();

                System.out.println("Introduce el nombre de la provincia: ");
                provincia = sc.nextLine();

                try {
                    System.out.println("Introduce el año");
                    int anyo = sc.nextInt();
                    sc.nextLine();


                    System.out.println("Introduce el mes");
                    int mes = sc.nextInt();
                    sc.nextLine();


                    System.out.println("Introduce el dia");
                    int dia = sc.nextInt();
                    sc.nextLine();

                    fechaConstruccion = LocalDate.of(anyo, mes, dia);
                } catch (Exception e) {
                    System.out.println("error al introducir la fecha");
                }

                System.out.println("Introduce el correo: ");
                correo = sc.nextLine();
                sc.nextLine();

                System.out.println("Introduce la valoracion, solo del 1 al 5: ");
                valoracion = sc.nextInt();
                sc.nextLine();

                switch (opcionNueva) {
                    case 1:
                        if (nuevoParqueAtracciones(sc, id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion))
                            opcionNueva = 3;
                        break;
                    case 2:
                        if (nuevoZoologico(sc, id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion))
                            opcionNueva = 3;
                        break;
                    default:
                        break;
                }

            }catch (InputMismatchException e) {
                System.out.println("Has introducido un dato invalido, Vuelvo a sacar el de añadir.");
                sc.next();
                opcionNueva = 0;
            }

            catch (Exception e) {
                System.out.println("por favor, introduzca una opcion valida");
                opcionNueva = 0;
            }
        }
    }

    private boolean nuevoParqueAtracciones(Scanner sc, int id, String nombre, int precioEntrada, String provincia, LocalDate fechaConstruccion, String correo, int valoracion) {
        if (fechaConstruccion != null) {
            System.out.println("Introduzca la edad minima para entrar");
            int edadminima = sc.nextInt();
            sc.nextLine();
            try {
                if (sv.addCentroOcio(new ParqueAtracciones(id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion, edadminima))) {
                    System.out.println("Agregado Correctamente");
                    return true;
                } else
                    System.out.println("Error al agregar");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else
            System.out.println("La fecha es incorrecta, no se ha agregado el parque");

        return false;
    }

    private boolean nuevoZoologico(Scanner sc, int id, String nombre, int precioEntrada, String provincia, LocalDate fechaConstruccion, String correo, int valoracion) {
        if (fechaConstruccion != null) {

            System.out.println("los servicios del zoologico separados por espacios");
            List<String> servicios = Arrays.asList(sc.nextLine(), " ");
            try {
                if (sv.addCentroOcio(new Zoologico(id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion, servicios))) {
                    System.out.println("Agregado Correctamente");

                    return true;
                } else
                    System.out.println("Error al agregar");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else
            System.out.println("La fecha es incorrecta, no se ha agregado el zoo");
        return false;
    }


    //Ejercicio 4.- Consulta de los centros de ocio que hay por provincia, el metodo o metodos informara de cuantos centros hay
    // y el precio base medio de la entrada que tiene una provincia
    private void ejercicio4() {
        List<CentroOcio> consulta = sv.consulta();
        List<String> provincias = consulta.stream().map(CentroOcio::getProvincia).toList();//obtengo todas las provincias
        int precioTotal;
        int numeroDeCentros;
        for (String provincia : provincias) {
            precioTotal = 0;
            numeroDeCentros = 0;
            for (CentroOcio centroOcio : consulta) {
                if (centroOcio.getProvincia().equalsIgnoreCase(provincia)) {//encuentro la provincia que tiene casa centro
                    //sumo al numero de centros de una provincia especifica
                    numeroDeCentros++;
                    //sumo el precio de la entrada de este centro
                    precioTotal = precioTotal + centroOcio.getPrecioEntrada();

                }
            }
            //el resultado de la suma del numero de centros en una provincia especifica y de la media de precio
            System.out.println("En la provincia " + provincia + ", hay un total de " + numeroDeCentros
                    + " centros y la media del precio de la entrada es " + precioTotal / numeroDeCentros);
        }
    }


    private void ejercicio5(Scanner sc) {
        System.out.println("Introduce el id del zoologico al que quieres añadirle un servicio.");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce el nombre del nuevo servicio: ");
        String servicioNuevo = sc.next();

        if (sv.actualizarServicios(id, servicioNuevo))
            System.out.println("todo bien");
        else
            System.out.println("Ha habido un error al intentar meter el servicio, quizas el id no existe?");

    }


    private void ejercicio6(Scanner sc) {

        System.out.println("Introduce la edad a comparar");
        int edad = sc.nextInt();
        sc.nextLine();

        System.out.println("true o false si el dia es festivo");
        boolean isfestivo = sc.nextBoolean();
        sc.nextLine();

        System.out.println(
                "Lista: "+
                sv.listadoOrdenado(edad, isfestivo).toString()
        );
    }


    private void ejercicio7(Scanner sc) {
        System.out.println("Introduzca hasta que año quieres borrar los centros");
        int anyo = sc.nextInt();
        sc.nextLine();
        System.out.println("se han borrado " + sv.removeCentrosOcio(anyo) + " centros.");
    }


    private void ejercicio8() {
        if (sv.guardarTXT())
            System.out.println("Todo se ha guardado correctamente");
        else
            System.out.println("Error al guardar el archivo de texto.");
    }


    private void ejercicio9() {
        if(sv.escribirBinario())
            System.out.println("Todo se ha guardado correctamente");
        else
            System.out.println("Error al guardar el archivo binario.");
    }


    private void ejercicio10() {
        if (sv.escribirJSON())
            System.out.println("Todo se ha guardado correctamente");
        else
            System.out.println("Error al guardar el archivo JSON.");
    }


    private void ejercicio11() {
        for (ParqueAtracciones parqueAtracciones : sv.leerJSON()) {
            System.out.println(parqueAtracciones.toString());
        }
    }


    private void ejercicio12() {
        if (sv.leerBinario())
            System.out.println("Todo se ha cargado correctamente");
        else
            System.out.println("Error al cargar el archivo binario.");
    }


    private void ejercicio13() {
        if (sv.cargarFicheroTexto())
            System.out.println("Todo se ha cargado correctamente");
        else
            System.out.println("Error al cargar el archivo de texto.");
    }

}

