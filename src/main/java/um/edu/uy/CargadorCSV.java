package um.edu.uy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import um.edu.uy.entities.*;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.HashTableCerrada;

public class CargadorCSV {


    public static void cargarUsuarios(
            HashTableCerrada<Usuario, Usuario> hashUsuarios,
            HashTableCerrada<Evaluacion, Evaluacion> hashEvaluaciones,
            HashTableCerrada<Pelicula, Pelicula> hashPeliculas
    ) {
        Path path = Paths.get("./ratings_1mm.csv");

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",");
                try {
                    int idUsuario = Integer.parseInt(partes[0].trim());
                    int idPelicula = Integer.parseInt(partes[1].trim());
                    float valoracion = Float.parseFloat(partes[2].trim());
                    long timestamp = Long.parseLong(partes[3].trim());

                    LocalDate fecha = LocalDate.ofEpochDay(timestamp / 86400);
                    Usuario usuario = new Usuario(idUsuario);
                    hashUsuarios.agregar(usuario, usuario);
                    Pelicula pelicula = hashPeliculas.obtener(new Pelicula(idPelicula));
                    if (pelicula != null) {
                        Usuario usuario1 = new Usuario(idUsuario);
                        hashUsuarios.agregar(usuario, usuario);

                        Evaluacion evaluacion = new Evaluacion(pelicula, usuario, valoracion, fecha);
                        hashEvaluaciones.agregar(evaluacion, evaluacion);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Línea con valores no numéricos: " + linea);
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo ratings_1mm.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void cargarPeliculas(HashTableCerrada<Pelicula, Pelicula> hashPeliculas) {
        Path pathPeliculas = Paths.get("movies_metadata.csv");

        try (BufferedReader br = Files.newBufferedReader(pathPeliculas, StandardCharsets.UTF_8)) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (partes.length < 21) {
                    System.out.println("Fila con columnas insuficientes: " + partes.length);
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[5].trim());
                    String titulo = partes[20].trim();
                    String genero = partes[3].trim();
                    String idiomaOriginal = partes[7].trim();
                    long ingreso = partes[14].trim().isEmpty() ? 0 : Long.parseLong(partes[14].trim());

                    LocalDate fecha = null;
                    if (!partes[12].trim().isEmpty()) {
                        try {
                            fecha = LocalDate.parse(partes[12].trim());
                        } catch (Exception e) {
                            System.out.println("Fecha inválida: " + partes[12].trim());
                        }
                    }

                    Director director = null;
                    Pelicula pelicula = new Pelicula(id, titulo, genero, idiomaOriginal, ingreso, fecha, director);
                    hashPeliculas.agregar(pelicula, pelicula);
                    System.out.println("Película agregada: " + titulo);

                } catch (Exception e) {
                    System.err.println("Error al procesar línea: " + linea);
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }


}
