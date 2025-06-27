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

        String respuesta = "TOP 5 PELICULAS POR IDIOMA" + "\n" +
                "-----------------------------------" + "\n";

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

            respuesta += "-------------------------------" + "\n" +
                    "Top 5 para: " + idioma + "\n";
            for (int i = 0; i < 5 && !heapDePelis.isEmpty(); i++) {
                PeliculaPorEvaluaciones topMasEvaluada = heapDePelis.obtenerYEliminar();
                Pelicula top = topMasEvaluada.getPelicula();
                respuesta += "Id" + top.getId() + "\n"
                        + "Titulo: " + top.getTitulo() + "\n"
                        + "Evaluacion: " + top.cantidadDeEvaluaciones() + "\n"
                        + "Idioma: " + idioma + "\n";

            }
        }
        return respuesta;
    }

    //2
    public String top10MejoresPeliculasCalificadasMedia() {
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
        for (int i = 0; i < 10; i++) {
            top10[i] = heapCalificacionesPeliculas.obtenerYEliminar();
        }
        String resultado = "TOP 10 PELICULAS POR CALIFICACIÓN:" + "\n" +
                "------------------------------------+" + "\n";
        for (int posPelicula = 0; posPelicula < top10.length; posPelicula++) {
            Pelicula pelicula = top10[posPelicula];
            resultado += (posPelicula + 1) + "- " + pelicula.toString() + "\n";
        }
        return resultado;
    }

    //3
    public String getTopSagas() {
        Object[] arrayObjects = sagaHash.getValuesArray();
        Saga[] sagaArray = new Saga[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            sagaArray[i] = (Saga) arrayObjects[i];
        }
        //Saga[] sagaArray = (Saga[]) sagaHash.getValuesArray();

        if (sagaArray.length < 10) {
            System.out.println("No hay suficientes sagas");
            return null;
        }
        Saga[] top10 = new Saga[10];

        Heap<Saga> heapSagas = new Heap<>(sagaArray.length, false);
        for (Saga saga : sagaArray) {
            heapSagas.agregar(saga);
        }
        for (int i = 0; i < 10; i++) {
            top10[i] = heapSagas.obtenerYEliminar();
        }
        String resultado = "TOP 5 SAGAS POR INGRESOS" + "\n" +
                "------------------------------------" + "\n";

        for (int posSaga = 0; posSaga < top10.length; posSaga++) {
            Saga saga = top10[posSaga];
            resultado += (posSaga + 1) + "- " + saga.toString() + "\n";
        }
        return resultado;
    }

    //4
    public String top10MejoresDirectoresCalificacionMedia() {
        Object[] arrayObjects = directorHash.getValuesArray();
        Director[] directorArray = new Director[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            directorArray[i] = (Director) arrayObjects[i];
        }

        if (directorArray.length < 10) {
            System.out.println("No hay suficientes directors");
            return null;
        }

        Director[] top10 = new Director[10];
        Heap<Director> heapCalificacionesDirector = new Heap<>(directorArray.length, false);

        for (Director director : directorArray) {
            ListaEnlazada<Pelicula> peliculas = director.getPeliculasdelDirector();
            if (peliculas.tamanio() > 1 && director.totalEvaluaciones() > 100) {
                heapCalificacionesDirector.agregar(director);
            }
        }
        if (heapCalificacionesDirector.obtenerTamanio() < 10) {
            return ("No hay suficientes directors");
        }

        for (int i = 0; i < 10; i++) {
            top10[i] = heapCalificacionesDirector.obtenerYEliminar();
        }
        String resultado = "TOP 10 DIRECTORES POR CALIFICACIÓN:" + "\n" +
                "------------------------------------+" + "\n";
        for (int directorPos = 0; directorPos < top10.length; directorPos++) {
            Director director = top10[directorPos];
            resultado += (directorPos + 1) + "- " + director.toString() + "\n";
        }
        return resultado;
    }

    //5

    public String mejorActorPorMes() {
        //Actor[] actorArray = (Actor[]) actorHash.getValuesArray();
        Object[] arrayObjects = actorHash.getValuesArray();
        Actor[] actorArray = new Actor[arrayObjects.length];
        for (int i = 0; i < arrayObjects.length; i++) {
            actorArray[i] = (Actor) arrayObjects[i];
        }

        Actor[] mejores = new Actor[12];
        int[] maxCalificacionesPorMes = new int[12];
        for (int meses = 0; meses < 12; meses++) {
            maxCalificacionesPorMes[meses] = -1;
        }

        for (Actor actor : actorArray) {
            if (actor != null) {
                int[] calificacionesActorMes = actor.calificacionPorMes(); //array con todas las calificaciones del actor x mes
                for (int mes = 0; mes < 12; mes++) {
                    if (calificacionesActorMes[mes] > maxCalificacionesPorMes[mes]) {
                        maxCalificacionesPorMes[mes] = calificacionesActorMes[mes];
                        mejores[mes] = actor;

                    }
                }
            }
        }
        String resultado = "ACTORES PRINCIPALES POR MES" + "\n" +
                "---------------------------------------" + "\n";

        for (int meses = 0; meses < 12; meses++) {
            resultado += "Mes: " + (meses + 1) + "\n"
                    + "Actor: " + mejores[meses].toString() + "\n"
                    + "Cantidad de Peliculas (distintas): " + mejores[meses].getPeliculasDistintasPorMes(meses) + "\n"
                    + "Cantidad de Calificaciones: " + maxCalificacionesPorMes[meses] + "\n";
        }
        return resultado;
    }

    //6
    public String topUsuariosConMasClasificacionesPorGenero() {
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

        String resultado = "USUARIO CON MAS CLASIFICACIONES POR GENERO\n" +
                "--------------------------------------------\n";

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
                        int userId = (int) usuario.getId(); // ← tratamos el ID como int

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

            resultado += "Genero: " + genero + "\n";
            if (!heapUsuarios.isEmpty()) {
                Pair<Integer, Integer> topUser = heapUsuarios.obtenerYEliminar();
                Usuario u = usuarioHash.obtener(topUser.getKey());
                if (u != null) {
                    resultado += u.toString();
                    resultado += "Cantidad de Clasificaciones: " + topUser.getValue() + "\n";
                } else {
                    resultado += "Usuario desconocido (ID: " + topUser.getKey() + ") → "
                            + topUser.getValue() + " clasificaciones\n";
                }
            } else {
                resultado += "No se encontraron usuarios para este género.\n";
            }
            resultado += "\n";
        }

        return resultado;
    }

}












