package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.*;


public class Consultas {
    private HashTableCerrada<Integer, Pelicula> hashPeliculas;
    private HashTableCerrada<Integer, Saga> sagaHash;
    private HashTableCerrada<String, Director> directorHash;
    private HashTableCerrada<String, Actor> actorHash;
    private HashTableCerrada<Integer, Usuario> usuarioHash;

    public Consultas(HashTableCerrada<Integer, Pelicula> hashPeliculas, HashTableCerrada<Integer, Saga> sagaHash, HashTableCerrada<String, Director> directorHash, HashTableCerrada<String, Actor> actorHash, HashTableCerrada<Integer, Usuario> usuarioHash) {
        this.hashPeliculas = hashPeliculas;
        this.sagaHash = sagaHash;
        this.directorHash = directorHash;
        this.actorHash = actorHash;
        this.usuarioHash = usuarioHash;
    }

    //1
    public String Top5PorIdioma() {
        String[] idiomas = {"en", "fr", "it", "es", "pt"};

        long start = System.currentTimeMillis();
        StringBuilder respuesta = new StringBuilder();

        for (String idioma : idiomas) {
            Heap<PeliculaPorEvaluaciones> heapDePelis = new Heap<>(hashPeliculas.tamanio(), false);

            Object[] arrayObjects = hashPeliculas.getValuesArray();
            Pelicula[] arrayPeliculas = new Pelicula[arrayObjects.length];

            for (int i = 0; i < arrayObjects.length; i++) {
                arrayPeliculas[i] = (Pelicula) arrayObjects[i];
            }
            for (Pelicula peli : arrayPeliculas) {
                if (peli != null && idioma.equals(peli.getIdiomaOriginal())) {
                    heapDePelis.agregar(new PeliculaPorEvaluaciones(peli));
                }
            }
            for (int i = 0; i < 5 && !heapDePelis.isEmpty(); i++) {
                PeliculaPorEvaluaciones topMasEvaluada = heapDePelis.obtenerYEliminar();
                Pelicula top = topMasEvaluada.getPelicula();
                respuesta.append(top.getId()).append(", ")
                        .append(top.getTitulo()).append(",")
                        .append(top.cantidadDeEvaluaciones()).append(",")
                        .append(idioma).append("\n");
            }
        }

        long end = System.currentTimeMillis();
        respuesta.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return respuesta.toString();
    }

    //2
    public String top10MejoresPeliculasCalificadasMedia() {
        long start = System.currentTimeMillis();
        Object[] arrayObjects = hashPeliculas.getValuesArray();
        Pelicula[] arrayPeliculas = new Pelicula[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            arrayPeliculas[i] = (Pelicula) arrayObjects[i];
        }

        if (arrayPeliculas.length < 10) {
            System.out.println("No hay suficientes peliculas");
            return null;
        }

        Pelicula[] top10 = new Pelicula[10];

        Heap<Pelicula> heapCalificacionesPeliculas = new Heap<>(arrayPeliculas.length, false);
        for (Pelicula pelicula : arrayPeliculas) {
            if (pelicula.cantidadDeEvaluaciones() > 100) {
                heapCalificacionesPeliculas.agregar(pelicula);
            }
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < 10 && !heapCalificacionesPeliculas.isEmpty(); i++) {
            Pelicula peli = heapCalificacionesPeliculas.obtenerYEliminar();
            resultado.append(peli.getId()).append(", ")
                    .append(peli.getTitulo()).append(",")
                    .append(String.format("%.2f", peli.calificacionMedia())).append("\n");
        }

        long end = System.currentTimeMillis();
        resultado.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return resultado.toString();
    }

    //3
    public String getTopSagas() {
        long start = System.currentTimeMillis();

        Object[] arrayObjects = sagaHash.getValuesArray();
        Saga[] sagaArray = new Saga[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            sagaArray[i] = (Saga) arrayObjects[i];
        }

        Heap<Saga> heapSagas = new Heap<>(sagaArray.length, false);
        for (Saga saga : sagaArray) {
            if (saga != null) {
                heapSagas.agregar(saga);
            }
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < 5 && !heapSagas.isEmpty(); i++) {
            Saga saga = heapSagas.obtenerYEliminar();


            StringBuilder listaIds = new StringBuilder();
            listaIds.append("[");
            ListaEnlazada<Pelicula> pelis = saga.getPeliculas();
            for (int j = 0; j < pelis.tamanio(); j++) {
                try {
                    listaIds.append(pelis.obtenervalorposicion(j).getId());
                    if (j < pelis.tamanio() - 1) listaIds.append(",");
                } catch (FueraDeRango e) {
                    e.printStackTrace();
                }
            }
            listaIds.append("]");

            resultado.append(saga.getId()).append(",\n");  // línea 1: solo id
            resultado.append(saga.getNombre()).append(",")
                    .append(pelis.tamanio()).append(",")
                    .append(listaIds).append(saga.sumaIngreso()).append("\n");
        }

        long end = System.currentTimeMillis();
        resultado.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return resultado.toString();
    }

    //4
    public String top10MejoresDirectoresCalificacionMedia() {
        long start = System.currentTimeMillis();

        Object[] arrayObjects = directorHash.getValuesArray();
        Director[] directorArray = new Director[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            directorArray[i] = (Director) arrayObjects[i];
        }

        Heap<Director> heapCalificacionesDirector = new Heap<>(directorArray.length, false);

        for (Director director : directorArray) {
            if (director == null) continue;
            ListaEnlazada<Pelicula> peliculas = director.getPeliculasdelDirector();
            if (peliculas.tamanio() > 1 && director.totalEvaluaciones() > 100) {
                heapCalificacionesDirector.agregar(director);
            }
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < 10 && !heapCalificacionesDirector.isEmpty(); i++) {
            Director director = heapCalificacionesDirector.obtenerYEliminar();
            resultado.append(director.getNombre()).append(",")
                    .append(director.getPeliculasdelDirector().tamanio()).append(",")
                    .append(String.format("%.2f", director.calificacionMedia())).append("\n");
        }

        long end = System.currentTimeMillis();
        resultado.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return resultado.toString();
    }

    //5

    public String mejorActorPorMes() {
        long start = System.currentTimeMillis();

        Object[] arrayObjects = actorHash.getValuesArray();
        Actor[] actorArray = new Actor[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            actorArray[i] = (Actor) arrayObjects[i];
        }

        Actor[] mejores = new Actor[12];
        int[] maxCalificacionesPorMes = new int[12];
        for (int mes = 0; mes < 12; mes++) {
            maxCalificacionesPorMes[mes] = -1;
        }

        for (Actor actor : actorArray) {
            if (actor != null) {
                int[] calificacionesActorMes = actor.calificacionPorMes();
                for (int mes = 0; mes < 12; mes++) {
                    if (calificacionesActorMes[mes] > maxCalificacionesPorMes[mes]) {
                        maxCalificacionesPorMes[mes] = calificacionesActorMes[mes];
                        mejores[mes] = actor;
                    }
                }
            }
        }

        StringBuilder resultado = new StringBuilder();

        for (int mes = 0; mes < 12; mes++) {
            Actor actor = mejores[mes];
            if (actor != null) {
                resultado.append((mes + 1)).append(",")
                        .append(actor.getNombre()).append(",")
                        .append(actor.getPeliculasDistintasPorMes(mes)).append(",")
                        .append(maxCalificacionesPorMes[mes]).append("\n");
            }
        }

        long end = System.currentTimeMillis();
        resultado.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return resultado.toString();
    }

    //6
    public String topUsuariosConMasClasificacionesPorGenero() {
        long start = System.currentTimeMillis();

        HashTableCerrada<String, Integer> conteoGeneros = new HashTableCerrada<>(50);

        Object[] peliculas = hashPeliculas.getValuesArray();
        for (Object obj : peliculas) {
            Pelicula peli = (Pelicula) obj;
            if (peli == null || peli.getGenero() == null) continue;

            ListaEnlazada<String> generos = peli.getGenero();
            for (int i = 0; i < generos.tamanio(); i++) {
                try {
                    String genero = generos.obtenervalorposicion(i);
                    Integer conteo = conteoGeneros.obtener(genero);
                    if (conteo == null) conteo = 0;
                    conteoGeneros.agregar(genero, conteo + peli.getEvaluaciones().tamanio());
                } catch (FueraDeRango e) {
                    e.printStackTrace();
                }
            }
        }

        Heap<Pair<String, Integer>> heapGeneros = new Heap<>(conteoGeneros.tamanio(), false);
        ListaEnlazada<NodoHash<String, Integer>>[] tablaGeneros = conteoGeneros.getEntryArray();

        for (ListaEnlazada<NodoHash<String, Integer>> lista : tablaGeneros) {
            if (lista == null) continue;
            try {
                for (int i = 0; i < lista.tamanio(); i++) {
                    NodoHash<String, Integer> nodo = lista.obtenervalorposicion(i);
                    heapGeneros.agregar(new Pair<>(nodo.getClave(), nodo.getValor()));
                }
            } catch (FueraDeRango e) {
                e.printStackTrace();
            }
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < 10 && !heapGeneros.isEmpty(); i++) {
            Pair<String, Integer> parGenero = heapGeneros.obtenerYEliminar();
            String genero = parGenero.getKey();

            HashTableCerrada<Integer, Integer> conteoUsuario = new HashTableCerrada<>(usuarioHash.tamanio());

            for (Object obj : peliculas) {
                Pelicula peli = (Pelicula) obj;
                if (peli == null || peli.getGenero() == null) continue;
                if (!peli.getGenero().contiene(genero)) continue;

                ListaEnlazada<Evaluacion> evaluaciones = peli.getEvaluaciones();
                for (int j = 0; j < evaluaciones.tamanio(); j++) {
                    try {
                        Evaluacion eval = evaluaciones.obtenervalorposicion(j);
                        Usuario usuario = eval.getUser();
                        if (usuario == null) continue;
                        int userId = usuario.getId();

                        Integer cantidad = conteoUsuario.obtener(userId);
                        if (cantidad == null) cantidad = 0;
                        conteoUsuario.agregar(userId, cantidad + 1);
                    } catch (FueraDeRango e) {
                        e.printStackTrace();
                    }
                }
            }

            Heap<Pair<Integer, Integer>> heapUsuarios = new Heap<>(conteoUsuario.tamanio(), false);
            ListaEnlazada<NodoHash<Integer, Integer>>[] tablaUsuarios = conteoUsuario.getEntryArray();

            for (ListaEnlazada<NodoHash<Integer, Integer>> lista : tablaUsuarios) {
                if (lista == null) continue;
                try {
                    for (int m = 0; m < lista.tamanio(); m++) {
                        NodoHash<Integer, Integer> nodo = lista.obtenervalorposicion(m);
                        heapUsuarios.agregar(new Pair<>(nodo.getClave(), nodo.getValor()));
                    }
                } catch (FueraDeRango e) {
                    e.printStackTrace();
                }
            }

            if (!heapUsuarios.isEmpty()) {
                Pair<Integer, Integer> topUser = heapUsuarios.obtenerYEliminar();
                resultado.append(topUser.getKey()).append(",")
                        .append(genero).append(",")
                        .append(topUser.getValue()).append("\n");
            }
        }

        long end = System.currentTimeMillis();
        resultado.append("Tiempo de ejecución de la consulta: ").append(end - start);
        return resultado.toString();
    }
}












