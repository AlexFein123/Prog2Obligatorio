package um.edu.uy;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.nio.charset.StandardCharsets;
import um.edu.uy.entities.*;
import um.edu.uy.tads.*;
import um.edu.uy.exceptions.*;
import um.edu.uy.CSVUtils;
import org.json.*;
import com.opencsv.CSVReader;

public class CargadorCSV {

    public static void cargarUsuariosYEvaluaciones(
            HashTableCerrada<Integer, Usuario> usuarios,
            HashTableCerrada<Integer, Pelicula> peliculas,
            ListaEnlazada<Evaluacion> evaluaciones
    ) {
        Path path = Paths.get(System.getProperty("user.dir")).resolve("./ratings_1mm.csv");

        int evaluacionesIgnoradasPorFaltaPelicula = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String linea = br.readLine(); // Encabezado

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                int idUsuario = Integer.parseInt(partes[0]);
                int idPelicula = Integer.parseInt(partes[1]);
                float rating = Float.parseFloat(partes[2]);
                long timestamp = Long.parseLong(partes[3]);
                LocalDate fecha = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();

                Usuario user = usuarios.obtener(idUsuario);
                if (user == null) {
                    user = new Usuario(idUsuario);
                    usuarios.agregar(idUsuario, user);
                }

                Pelicula pelicula = peliculas.obtener(idPelicula);
                if (pelicula == null) {
                    evaluacionesIgnoradasPorFaltaPelicula++;
                    continue;
                }

                Evaluacion evaluacion = new Evaluacion(pelicula, user, rating, fecha);
                evaluaciones.agregarAlFinal(evaluacion);
                pelicula.getEvaluaciones().agregarAlFinal(evaluacion);
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de evaluaciones: " + e.getMessage());
        }

        System.out.println("Evaluaciones ignoradas porque no se encontró la película: " + evaluacionesIgnoradasPorFaltaPelicula);
    }

    public static void cargarPeliculas(HashTableCerrada<Integer, Pelicula> hashPeliculas,
                                       HashTableCerrada<Integer, Saga> sagas) {
        String archivo = "movies_metadata.csv";
        int peliculasNoCargadas = 0;

        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] encabezado = reader.readNext();
            String[] fila;

            while ((fila = reader.readNext()) != null) {
                if (fila.length < 19) {
                    peliculasNoCargadas++;
                    continue;
                }

                int id;
                try {
                    id = Integer.parseInt(fila[5].trim());
                } catch (NumberFormatException nfe) {
                    peliculasNoCargadas++;
                    continue;
                }

                try {
                    String titulo = fila[8];
                    String idiomaOriginal = fila[7];
                    long ingreso = 0;
                    if (!fila[13].isEmpty()) {
                        try {
                            ingreso = Long.parseLong(fila[13]);
                        } catch (NumberFormatException e) {
                            // Si falla el parseo de ingreso, dejarlo en 0
                        }
                    }

                    LocalDate fecha = null;
                    if (!fila[12].isEmpty()) {
                        try {
                            fecha = LocalDate.parse(fila[12]);
                        } catch (DateTimeParseException dtpe) {
                            // Dejar fecha como null si no se puede parsear
                        }
                    }

                    ListaEnlazada<String> generos = new ListaEnlazada<>();
                    try {
                        String generosRaw = fila[3].replace("'", "\"");
                        JSONArray generosJSON = new JSONArray(generosRaw);
                        for (int i = 0; i < generosJSON.length(); i++) {
                            JSONObject generoObj = generosJSON.getJSONObject(i);
                            generos.agregarAlFinal(generoObj.getString("name"));
                        }
                    } catch (Exception e) {
                        // Si falla, se deja la lista vacía
                    }

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
                        // Si falla, se deja la lista vacía
                    }

                    Director director = null; // Se asigna en otro método (credits.csv)

                    // ---- Carga segura de saga ----
                    Saga saga = null;
                    String coleccionRaw = fila[1]; // belongs_to_collection
                    if (coleccionRaw != null && !coleccionRaw.isEmpty()) {
                        try {
                            coleccionRaw = coleccionRaw.replace("'", "\"").trim();
                            JSONObject coleccionJSON = new JSONObject(coleccionRaw);
                            int idSaga = coleccionJSON.getInt("id");
                            String nombreSaga = coleccionJSON.getString("name");

                            saga = sagas.obtener(idSaga);
                            if (saga == null) {
                                saga = new Saga(idSaga, nombreSaga);
                                sagas.agregar(idSaga, saga);
                            }
                        } catch (Exception e) {
                            // Si hay error parseando la saga, no se asigna
                        }
                    }

                    Pelicula p = new Pelicula(id, titulo, generos, idiomaOriginal, ingreso, fecha, director, idiomas, saga);
                    hashPeliculas.agregar(id, p);

                    // Si tiene saga, agregar la película a la saga
                    if (saga != null) {
                        saga.getPeliculas().agregarAlFinal(p);
                    }

                } catch (Exception e) {
                    peliculasNoCargadas++;
                }
            }

        } catch (Exception e) {
            System.err.println("Error al leer el archivo de películas: " + e.getMessage());
        }

        System.out.println("Cantidad de películas NO cargadas por error de formato: " + peliculasNoCargadas);
    }

    public static void cargarCreditos(
            HashTableCerrada<Integer, Pelicula> peliculas,
            HashTableCerrada<String, Actor> actoresGlobal,
            HashTableCerrada<String, Director> directoresGlobal
    ) {
        int errores = 0;
        Path path = Paths.get("credits.csv");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8));) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }

                String[] partes = CSVUtils.parseCSVLine(linea);
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

                try {
                    castRaw = CSVUtils.arreglarJsonFalso(castRaw);
                    JSONArray castArray = new JSONArray(castRaw);

                    for (int i = 0; i < castArray.length(); i++) {
                        JSONObject actorObj = castArray.getJSONObject(i);
                        if (!actorObj.has("name")) continue;

                        String nombre = actorObj.getString("name").trim();
                        if (nombre.isEmpty()) continue;

                        nombre = CSVUtils.corregirEncoding(nombre);

                        Actor actor = actoresGlobal.obtener(nombre);
                        if (actor == null) {
                            actor = new Actor(Math.random(), nombre);
                            actoresGlobal.agregar(nombre, actor);
                        }

                        actor.agregarPelicula(pelicula);
                        pelicula.getActores().agregar(actor);
                    }
                } catch (Exception e) {
                    errores++;
                }

                try {
                    crewRaw = CSVUtils.arreglarJsonFalso(crewRaw);
                    JSONArray crewArray = new JSONArray(crewRaw);

                    for (int i = 0; i < crewArray.length(); i++) {
                        JSONObject crewObj = crewArray.getJSONObject(i);
                        if (crewObj.has("job") && "Director".equalsIgnoreCase(crewObj.getString("job"))) {
                            String nombre = crewObj.getString("name").trim();
                            if (nombre.isEmpty()) continue;

                            nombre = CSVUtils.corregirEncoding(nombre);

                            Director director = directoresGlobal.obtener(nombre);
                            if (director == null) {
                                director = new Director(Math.random(), nombre);
                                directoresGlobal.agregar(nombre, director);
                            }

                            pelicula.setDirector(director);
                            break;
                        }
                    }

                } catch (Exception e) {
                    errores++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo archivo credits.csv: " + e.getMessage());
        }
        System.out.println("Cantidad de errores parseando actores/directores: " + errores);
    }
}
