package um.edu.uy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Usuario;
import um.edu.uy.tads.ListaEnlazada;
import org.json.JSONArray;
import org.json.JSONObject;
import com.opencsv.CSVReader;

import um.edu.uy.entities.*;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.HashTableCerrada;

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

    public static void cargarPeliculas(HashTableCerrada<Integer, Pelicula> hashPeliculas) {
        String archivo = "movies_metadata.csv";
        int peliculasnocargadas = 0;
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] encabezado = reader.readNext(); // Leer encabezado
            String[] fila;

            while ((fila = reader.readNext()) != null) {
                if (fila.length < 19) {
                    System.err.println("Fila con columnas insuficientes (" + fila.length + "): se ignora.");
                    continue;
                }

                int id;
                try {
                    id = Integer.parseInt(fila[5].trim());
                } catch (NumberFormatException nfe) {
                    peliculasnocargadas++;
                    System.err.println("ID inválido en fila, no es número: \"" + fila[5] + "\". Se ignora.");
                    continue;
                }

                try {
                    String titulo = fila[8];
                    String idiomaOriginal = fila[7];

                    // Ingreso (revenue)
                    long ingreso = 0;
                    if (!fila[13].isEmpty()) {
                        try {
                            ingreso = Long.parseLong(fila[13]);
                        } catch (NumberFormatException e) {
                            peliculasnocargadas++;
                            System.err.println("Ingreso inválido en fila ID " + id + ": " + fila[13]);
                        }
                    }

                    // Fecha (release_date)
                    LocalDate fecha = null;
                    if (!fila[12].isEmpty()) {
                        try {
                            fecha = LocalDate.parse(fila[12]);
                        } catch (DateTimeParseException dtpe)
                        {
                            peliculasnocargadas++;
                            System.err.println("Fecha inválida en fila ID " + id + ": \"" + fila[12] + "\"");
                        }
                    }

                    // Géneros
                    ListaEnlazada<String> generos = new ListaEnlazada<>();
                    try {
                        String generosRaw = fila[3].replace("'", "\"");
                        JSONArray generosJSON = new JSONArray(generosRaw);
                        for (int i = 0; i < generosJSON.length(); i++) {
                            JSONObject generoObj = generosJSON.getJSONObject(i);
                            generos.agregarAlFinal(generoObj.getString("name"));
                        }
                    } catch (Exception e) {
                        peliculasnocargadas++;
                        System.err.println("Error procesando géneros en fila ID " + id);
                    }

                    // Idiomas hablados
                    ListaEnlazada<String> idiomas = new ListaEnlazada<>();
                    try {
                        String idiomasRaw = fila[15].replace("'", "\"").trim();
                        if (!idiomasRaw.equals("[]") && !idiomasRaw.isEmpty()) {
                            JSONArray idiomasJSON = new JSONArray(idiomasRaw);
                            for (int i = 0; i < idiomasJSON.length(); i++) {
                                JSONObject idiomaObj = idiomasJSON.getJSONObject(i);
                                idiomas.agregarAlFinal(idiomaObj.getString("name"));
                            }
                        }
                    } catch (Exception e) {
                        peliculasnocargadas++;
                        System.err.println("Error procesando idiomas hablados en fila ID " + id);
                    }

                    // Director (null por ahora)
                    Director director = null;

                    // Crear y agregar película
                    Pelicula p = new Pelicula(id, titulo, generos, idiomaOriginal, ingreso, fecha, director, idiomas);
                    hashPeliculas.agregar(id, p);

                } catch (Exception e) {
                    peliculasnocargadas++;
                    System.err.println("Error general al procesar fila con ID " + id + ": " + e.getMessage());
                    System.err.println("Contenido de la fila:");
                    for (int i = 0; i < fila.length; i++) {
                        System.err.println("  [" + i + "] = " + fila[i]);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error al leer el archivo de películas: " + e.getMessage());
        }
        System.out.println("Cantidad de peliculas no cargadas:" + peliculasnocargadas);
    }

    public static void cargarCreditos(
            HashTableCerrada<Integer, Pelicula> peliculas,
            HashTableCerrada<String, Actor> actoresGlobal,
            HashTableCerrada<String, Director> directoresGlobal
    ) {
        int a = 0;
        Path path = Paths.get("credits.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }

                String[] partes = parseCSVLine(linea);
                if (partes.length < 3) continue;

                String castRaw = partes[0];
                String crewRaw = partes[1];
                int idPelicula;

                try {
                    idPelicula = Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                Pelicula pelicula = peliculas.obtener(idPelicula);
                if (pelicula == null) continue;

                // --- Actores ---
                try {
                    castRaw = castRaw.replace("'", "\"");
                    JSONArray castArray = new JSONArray(castRaw);

                    for (int i = 0; i < castArray.length(); i++) {
                        JSONObject actorObj = castArray.getJSONObject(i);
                        if (!actorObj.has("name")) continue;

                        String nombre = actorObj.getString("name").trim();
                        if (nombre.isEmpty()) continue;

                        Actor actor = actoresGlobal.obtener(nombre);
                        if (actor == null) {
                            actor = new Actor(Math.random(), nombre);
                            actoresGlobal.agregar(nombre, actor);
                        }

                        actor.agregarPelicula(pelicula);
                        pelicula.getActores().agregar(actor);
                    }

                } catch (Exception e) {
                    a++;

                }

                // --- Director ---
                try {
                    crewRaw = crewRaw.replace("'", "\"");
                    JSONArray crewArray = new JSONArray(crewRaw);

                    for (int i = 0; i < crewArray.length(); i++) {
                        JSONObject crewObj = crewArray.getJSONObject(i);
                        if (crewObj.has("job") && "Director".equalsIgnoreCase(crewObj.getString("job"))) {
                            String nombre = crewObj.getString("name").trim();
                            if (nombre.isEmpty()) continue;

                            Director director = directoresGlobal.obtener(nombre);
                            if (director == null) {
                                director = new Director(Math.random(), nombre);
                                directoresGlobal.agregar(nombre, director);
                            }

                            pelicula.setDirector(director);
                            break; // solo un director
                        }
                    }

                } catch (Exception e) {
                    a++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo archivo credits.csv: " + e.getMessage());
        }
        System.out.printf("Cantidad de Actores y directores no cargados: " + a + "\n");
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