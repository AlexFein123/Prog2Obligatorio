package um.edu.uy;

import java.util.Scanner;

import um.edu.uy.entities.*;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.ListaEnlazada;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // SistemaUMovie sistema = new SistemaUMovie();

        while (true) {
            System.out.println("Seleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        long inicioCarga = System.currentTimeMillis();

                        HashTableCerrada<Integer, Usuario> hashUsuarios = new HashTableCerrada<>(100_000);
                        HashTableCerrada<Integer, Pelicula> hashPeliculas = new HashTableCerrada<>(100_000);
                        HashTableCerrada<String, Actor> hashActores = new HashTableCerrada<>(100_000);
                        HashTableCerrada<String, Director> hashDirectores = new HashTableCerrada<>(100_000);
                        ListaEnlazada<Evaluacion> listaEvaluaciones = new ListaEnlazada<>();

                        CargadorCSV.cargarPeliculas(hashPeliculas);
                        CargadorCSV.cargarUsuariosYEvaluaciones(hashUsuarios, hashPeliculas, listaEvaluaciones);
                        CargadorCSV.cargarCreditos(hashPeliculas, hashActores, hashDirectores);

                        System.out.println("Usuarios cargados: " + hashUsuarios.tamanio());
                        System.out.println("Películas cargadas: " + hashPeliculas.tamanio());
                        System.out.println("Evaluaciones cargadas: " + listaEvaluaciones.tamanio());
                        System.out.println("Actores cargados: " + hashActores.tamanio());
                        System.out.println("Directores cargados: " + hashDirectores.tamanio());
                        long finCarga = System.currentTimeMillis();
                        System.out.println("\nCarga de datos exitosa, tiempo de ejecución: " + (finCarga - inicioCarga) + " ms");
                    } catch (Exception e) {
                        System.err.println("Error durante la carga de datos:");
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    ejecutarConsultas(scanner);
                    break;
                case "3":
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void ejecutarConsultas(Scanner scanner) {
        while (true) {
            System.out.println("1. Top 5 de las películas que más calificaciones por idioma.");
            System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
            System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
            System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
            System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
            System.out.println("6. Usuarios con más calificaciones por género");
            System.out.println("7. Salir");

            String opcion = scanner.nextLine();

            long inicio = System.currentTimeMillis();
            switch (opcion) {
                case "1":
                    //sistema.topPeliculasPorIdioma();
                    break;
                case "2":
                    //sistema.topPeliculasPorCalificacionMedia();
                    break;
                case "3":
                    //sistema.topColeccionesPorIngresos();
                    break;
                case "4":
                    //sistema.topDirectoresPorMediana();
                    break;
                case "5":
                    //sistema.actorMasVistoPorMes();
                    break;
                case "6":
                    //sistema.usuariosTopPorGenero();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
            long fin = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
        }
    }
}




