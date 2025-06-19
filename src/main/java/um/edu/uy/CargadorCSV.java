package um.edu.uy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Usuario;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.ListaEnlazada;

public class CargadorCSV {

    public static void cargarUsuarios(HashTableAbierta<Usuario,Usuario>Hashusuarios) {
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
                    Hashusuarios.agregar(usuario,usuario);

                } catch (NumberFormatException e) {
                    System.err.println("Línea con id no numérico: " + partes[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public static void CargarPeliculas(HashTableAbierta<Pelicula,Pelicula>HashPeliculas) {
        Path path = Paths.get(System.getProperty("ratings_1mm.csv"));
        int id;
        String titulo;
        String genero;
        String idiomaOriginal;
        long ingreso;
        LocalDate fecha;
        ListaEnlazada<Actor> actores;
        Director director;

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