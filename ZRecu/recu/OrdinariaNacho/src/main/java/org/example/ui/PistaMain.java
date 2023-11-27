package org.example.ui;

import org.example.common.Constantes;
import org.example.common.DificultadExcepcion;
import org.example.domain.Pista;
import org.example.service.PistaService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PistaMain {
    PistaService service = new PistaService();
    public void run(){
        service.agregarValoresIniciales();
        int opcion=-1;
        while (opcion!=12) {
            Constantes.BIENVENIDA();
            System.out.println(Constantes.OPCION);
            Scanner sc = new Scanner(System.in);
            try {
                opcion=sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println(Constantes.ERROROPCION);
                run();
            }
            switch (opcion) {
                case 1:
                    List<Pista> lista= service.getListaPistas();
                    for (Pista pista:lista) {
                        System.out.println(pista);
                    }
                    break;
                case 2:
                    System.out.println("Introduce Provincia:");
                    String provincia= sc.next();
                    List<Pista> lista2= service.listadoOrdenadoProvinciaKm(provincia);
                    for (Pista pista:lista2) {
                        System.out.println(pista);
                    }
                    break;
                case 3:
                    System.out.println("Que tipo de pista quieres agregar? Alpino/Fondo");
                    String respuesta = sc.next();
                    if (respuesta.equalsIgnoreCase("Fondo")){
                        System.out.println("Provincia: ");
                        String provinciaa = sc.next();
                        System.out.println("Nombre: ");
                        String nombre = sc.next();
                        System.out.println("Pueblo1: ");
                        String pueblo1 = sc.next();
                        System.out.println("Pueblo2: ");
                        String pueblo2 = sc.next();
                        int id = -1;
                        try{
                            id = sc.nextInt();
                            sc.nextLine();
                        }catch (InputMismatchException e){
                            System.out.println("Eso no es un numero");
                            run();
                        }
                        service.nuevaPistaFondo(provinciaa,nombre,pueblo1,pueblo2,id);
                    } else if (respuesta.equalsIgnoreCase("Alpino")) {
                        System.out.println("ID: ");
                        int id = -1;
                        try{
                            id = sc.nextInt();
                            sc.nextLine();
                        }catch (InputMismatchException e){
                            System.out.println("Eso no es un numero");
                            run();
                        }
                        System.out.println("Provincia: ");
                        String provinciaa = sc.next();
                        System.out.println("Nombre: ");
                        String nombre = sc.next();
                        System.out.println("Dificultad: ");
                        String dificultad = sc.next();
                        try {
                            service.nuevaPistaAlpina(id,provinciaa,nombre,dificultad);
                        } catch (DificultadExcepcion e) {
                            System.out.println("Error al crear la nueva pista");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Provincia: ");
                    String nuevaprovincia = sc.next();
                    System.out.println("El total de metros es de "+service.consulta(nuevaprovincia));
                    break;
                case 5:
                    System.out.println("Nombre: ");
                    String nuevonombre = sc.next();
                    System.out.println("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    if (service.addPuebloListaPueblos(id,nuevonombre))
                        System.out.println("ok");
                    else
                        System.out.println("mal");
                    break;
                case 6:
                    System.out.println("ID: ");
                    int id2 = sc.nextInt();
                    sc.nextLine();
                    if (service.removePista(id2))
                        System.out.println("ok");
                    else
                        System.out.println("mal");
                    break;
                case 7:
                    service.cargarFichero();
                    break;
                case 8:
                    service.cargarFicheroBinario();
                    break;
                case 9:
                    service.escribirFicheroBinario();
                    break;
                case 10:
                    service.escribirFichero();
                    break;
                case 11:
                    break;
                default:
                    break;
            }
            System.out.println("\n\n\n");
        }
        System.out.println(Constantes.EXIT);
    }
}
