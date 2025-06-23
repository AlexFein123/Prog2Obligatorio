
package um.edu.uy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import um.edu.uy.entities.*;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.ListaEnlazada;

public class CargadorCSV {

    public static void cargarUsuarios(HashTableAbierta<Usuario, Usuario> Hashusuarios) {
        Path path = Paths.get(System.getProperty("ratings_1mm.csv"));

        try (java.io.BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            boolean primeraLinea = true;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                i++;
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",");
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    Usuario usuario = new Usuario(id);
                    Hashusuarios.agregar(usuario, usuario);

                } catch (NumberFormatException e) {
                    System.err.println("Línea con id no numérico: " + partes[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void CargarPeliculas(HashTableAbierta<Pelicula, Pelicula> HashPeliculas) {


        Path pathPeliculas = Paths.get("movies_metadata.csv");

        try (BufferedReader br = Files.newBufferedReader(pathPeliculas)) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String titulo = partes[1].trim();
                    String genero = partes[2].trim();
                    String idiomaOriginal = partes[3].trim();

                    long ingreso = 0;
                    if (!partes[4].trim().isEmpty()) {
                        ingreso = Long.parseLong(partes[4].trim());
                    }


                    LocalDate fecha = null;
                    try {
                        if (!partes[5].trim().isEmpty()) {
                            fecha = LocalDate.parse(partes[5].trim());
                        }
                    } catch (Exception e) {
                        fecha = null;
                    }

                    Director director = null;


                    Pelicula peli = new Pelicula(id, titulo, genero, idiomaOriginal, ingreso, fecha, director);


                    HashPeliculas.agregar(id, peli);

                } catch (NumberFormatException e) {
                    System.err.println("Línea con ID o ingreso inválido: " + linea);
                } catch (Exception e) {
                    System.err.println("Error al procesar película: " + linea);
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo movies_metadata.csv: " + e.getMessage());
        }

        int id;
        String titulo;
        String genero;
        String idiomaOriginal;
        long ingreso;
        LocalDate fecha;
        ListaEnlazada<Actor> actores;
        Director director;

        Path path = Paths.get(System.getProperty("ratings_1mm.csv"));

        try (java.io.BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            boolean primeraLinea = true;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                i++;
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",");
                try {
                    // int id = Integer.parseInt(partes[0].trim());
                    // Pelicula pelicula = new Usuario(id);
                    // HashPeliculas.agregar(pelicula,pelicula);

                } catch (NumberFormatException e) {
                    System.err.println("Línea con id no numérico: " + partes[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
