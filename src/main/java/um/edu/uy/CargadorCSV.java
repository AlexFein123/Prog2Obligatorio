package um.edu.uy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Director;
import um.edu.uy.tads.HashTableCerrada;

import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Usuario;
import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class CargadorCSV {

    public static void cargarUsuariosYEvaluaciones(
            HashTableCerrada<Integer, Usuario> usuarios,
            HashTableCerrada<Integer, Pelicula> peliculas,
            ListaEnlazada<Evaluacion> evaluaciones
    ) {
        Path path = Paths.get(System.getProperty("user.dir")).resolve("./ratings_1mm.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String linea = br.readLine(); // Encabezado

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                int idUsuario = Integer.parseInt(partes[0]);
                int idPelicula = Integer.parseInt(partes[1]);
                float rating = Float.parseFloat(partes[2]);
                long timestamp = Long.parseLong(partes[3]);
                LocalDate fecha = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();

                // Obtener o crear usuario
                Usuario user = usuarios.obtener(idUsuario);
                if (user == null) {
                    user = new Usuario(idUsuario);
                    usuarios.agregar(idUsuario, user);
                }

                // Obtener película real desde tabla
                Pelicula pelicula = peliculas.obtener(idPelicula);
                if (pelicula == null) {
                    // Si no se encuentra, se ignora la evaluación
                    continue;
                }

                Evaluacion evaluacion = new Evaluacion(pelicula, user, rating, fecha);
                evaluaciones.agregarAlFinal(evaluacion);
                pelicula.getEvaluaciones().agregarAlFinal(evaluacion);
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de evaluaciones: " + e.getMessage());
        }
    }

    public static void cargarPeliculas(HashTableCerrada<Pelicula, Pelicula> hashPeliculas) {
        String archivo = "movies_metadata.csv";

        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            List<String[]> lineas = reader.readAll();
            boolean primeraLinea = true;

            for (String[] partes : lineas) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (partes.length < 21) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[5].trim());
                    String titulo = partes[20].trim();
                    String genero = partes[3].trim();
                    String idiomaOriginal = partes[7].trim();

                    long ingreso = 0;
                    if (!partes[14].trim().isEmpty()) {
                        try {
                            ingreso = Long.parseLong(partes[14].trim());
                        } catch (NumberFormatException e) {
                            ingreso = 0;
                        }
                    }

                    LocalDate fecha = null;
                    if (!partes[12].trim().isEmpty()) {
                        try {
                            fecha = LocalDate.parse(partes[12].trim());
                        } catch (Exception e) {
                            // ignorar error de fecha
                        }
                    }

                    Director director = null; // dejar para más adelante
                    Pelicula pelicula = new Pelicula(id, titulo, genero, idiomaOriginal, ingreso, fecha, director);
                    hashPeliculas.agregar(pelicula, pelicula);

                } catch (Exception e) {
                    // ignorar errores por línea corrupta
                }
            }

        } catch (Exception e) {
            System.err.println("Error leyendo CSV con OpenCSV:");
            e.printStackTrace();
        }
    }
}



    public static Pelicula obtenerPeliculaPorId(int id, HashTableCerrada<Integer, Pelicula> peliculas) {
        return peliculas.obtener(id);
    }

    private static String[] parseCSVLine(String linea) {
        if (linea == null || linea.isEmpty()) {
            return new String[0];
        }

        ListaEnlazada<String> campos = new ListaEnlazada<>();
        StringBuilder campo = new StringBuilder();
        boolean enComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                // Si estamos en comillas y la siguiente es también comilla, es comilla escapada
                if (enComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    campo.append('"'); // Agrego comilla escapada
                    i++; // salto la siguiente comilla
                } else {
                    enComillas = !enComillas; // cambio estado de comillas
                }
            } else if (c == ',' && !enComillas) {
                // Fin del campo si no estamos en comillas
                campos.agregarAlFinal(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(c);
            }
        }
        // Agrego último campo
        campos.agregarAlFinal(campo.toString());

        // Paso la lista enlazada a arreglo String[]
        String[] resultado = new String[campos.tamanio()];
        for (int i = 0; i < campos.tamanio(); i++) {
            try {
                resultado[i] = campos.obtenervalorposicion(i);
            } catch (FueraDeRango e) {
                resultado[i] = "";
            }
        }

        return resultado;
    }

}
