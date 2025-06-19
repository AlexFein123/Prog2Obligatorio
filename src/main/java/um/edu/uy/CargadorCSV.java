package um.edu.uy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Usuario;
import um.edu.uy.tads.ListaEnlazada;

public class CargadorCSV {

    public static ListaEnlazada<Usuario> cargarUsuarios(String rutaArchivo) {
        ListaEnlazada<Usuario> usuarios = new ListaEnlazada<>();
        Path path = Paths.get(System.getProperty("user.dir")).resolve(rutaArchivo);

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
                    usuarios.agregarOrdenado(new Usuario(id));

                } catch (NumberFormatException e) {
                    System.err.println("Línea con id no numérico: " + partes[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarios;
    }
    public static ListaEnlazada<Pelicula> CargarPeliculas(String rutaArchivo) {
        ListaEnlazada<Pelicula> Pelicula = new ListaEnlazada<>();
        Path path = Paths.get(System.getProperty("user.dir")).resolve(rutaArchivo);

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
                    // Pelicula.agregarOrdenado(new Pelicula(id));

                } catch (NumberFormatException e) {
                    System.err.println("Línea con id no numérico: " + partes[0]);
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return Pelicula;
    }
}