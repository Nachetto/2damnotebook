package org.example.ui;

import org.example.common.Constantes;
import org.example.common.FechaException;
import org.example.service.VueloServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VueloMain {
    public void run(){
        int option=0;
        VueloServiceImpl srv = new VueloServiceImpl();
            try{
                Scanner sc = new Scanner(System.in);
                while (option!=14){
                    System.out.println(Constantes.BIENVENIDA);
                    option=sc.nextInt();
                    sc.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("Lista de vuelos: "+srv.getListaVuelos());
                        break;
                    case 2:
                        System.out.println("Origen");
                        String origen=sc.next();
                        sc.nextLine();
                        System.out.println("Destino");
                        String destino=sc.next();
                        sc.nextLine();
                        System.out.println("Precio 1");
                        Double precio1=sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Precio 2");
                        Double precio2=sc.nextDouble();
                        sc.nextLine();
                        System.out.println("El resultado de su consulta:\n"+srv.consulta(origen,destino,precio1,precio2));
                        break;
                    case 3:
                        //String tipo, int id, int maxpasajeros, double precioavg, String aereolinea, String origen,  String destino,  LocalDate fecha
                        System.out.println("Introduzca el tipo de vuelo:");
                        String tipo = sc.next();
                        sc.nextLine();
                        System.out.println("Introduzca el identificador:");
                        int idnuevo = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Introduzca el numero maximo de pasajeros del avion:");
                        int maxpasajeros = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Introduzca la media del precio por asiento:");
                        double precio=sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Introduzca el nombre de la aereolinea:");
                        String aereolinea = sc.next();
                        sc.nextLine();
                        System.out.println("Introduzca el país de origen:");
                        String pais1= sc.next();
                        sc.nextLine();
                        System.out.println("Introduzca el país de destino:");
                        String pais2= sc.next();
                        sc.nextLine();
                        System.out.println("Introduzca la fecha (año):");
                        //REGEX
                        int anyo = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Introduzca la fecha (mes):");
                        int mes = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Introduzca la fecha (dia):");
                        int dia = sc.nextInt();
                        sc.nextLine();
                        //REGEX
                        //String fechaza=""+anyo+""+mes+""+dia;
                        //String patron = "[2023-2050]{4}[01-12]{4}[01-30]{2}";
                        //Pattern pattern = Pattern.compile(patron);
                        //Matcher matcher = pattern.matcher(fechaza);
                        List<String>nuevasescalas=new ArrayList<>();
                        String respuestaza="";
                        if (tipo.equalsIgnoreCase("internacional")) {
                            int i=1;
                            while(respuestaza.equalsIgnoreCase("salir")){

                                System.out.println("Introduzca la "+i+" escala:");
                                respuestaza=sc.next();
                                sc.nextLine();
                                if (respuestaza.equalsIgnoreCase("salir"))
                                    nuevasescalas.add(respuestaza);
                                i++;
                            }
                        }
                        srv.addVuelo(srv.filtrandoNuevoVuelo(tipo,idnuevo,maxpasajeros,precio,aereolinea,pais1,pais2, LocalDate.of(anyo,mes,dia),nuevasescalas));
                        break;
                    case 4:
                        System.out.println("Id del vuelo: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Nombre nueva escala:");
                        String name=sc.next();
                        sc.nextLine();
                        if (srv.addEscala(id,name))
                            System.out.println("Escala añadida correctamente");
                        break;
                    case 5:
                        System.out.println("Origen:");
                        String origenrm=sc.next();
                        sc.nextLine();
                        System.out.println("Destino:");
                        String destinorm=sc.next();
                        sc.nextLine();
                        srv.removeVuelo(origenrm,destinorm);
                        break;
                    case 6:
                        if(srv.escribirFichero())
                            System.out.println("El fichero se ha guardado correctamente");;
                        break;
                    case 7:
                        if (srv.escribirFicheroBinario())
                            System.out.println("Todo guardado.");
                        break;
                    case 8:
                        srv.guardarListaDesdeBinarioGuardado(srv.cargarFicheroBinario());
                        break;
                    case 9:
                        srv.guardarListaDesdeTextoGuardado(srv.cargarFichero());
                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:
                        srv.escribirJSON();
                        break;
                    case 13:
                        srv.guardarListaDesdeJSONGuardado(srv.cargarJSON());
                        break;
                    case 14:
                        System.out.println("Hasta luego!");
                        break;
                }
                if (option!=14)
                    option=0;
            }sc.close();
        }catch (InputMismatchException e){
                System.out.println("Por favor, Introduce un número válido");
            } catch (FechaException e) {
                System.out.println("Error al crear el usuario: "+e.getMessage());
            }
    }
}
