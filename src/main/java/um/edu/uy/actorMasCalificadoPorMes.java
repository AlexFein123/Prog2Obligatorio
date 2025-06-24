package um.edu.uy;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.ListaEnlazada;

public class actorMasCalificadoPorMes {
    public static void ejecutar(ListaEnlazada<Evaluacion> listaEvaluaciones) {
        long inicio = System.currentTimeMillis();

        HashTableAbierta<String, HashTableAbierta<String, ActorStats>> actoresPorMes = new HashTableAbierta<>(12);

        Evaluacion[] evaluaciones = listaEvaluaciones.getValuesArray();

        for (Evaluacion eval : evaluaciones) {
            if (eval == null) continue;

            Pelicula peli = eval.getPelicula();
            if (peli == null) continue;

            String mes = String.format("%02d", eval.getFecha().getMonthValue());

            HashTableAbierta<String, ActorStats> tablaActores = actoresPorMes.obtener(mes);
            if (tablaActores == null) {
                tablaActores = new HashTableAbierta<>(100);
                actoresPorMes.agregar(mes, tablaActores);
            }

            ListaEnlazada<String> actoresYaContados = new ListaEnlazada<>();

            ListaEnlazada<Actor> actores = peli.getActores();
            if (actores == null) continue;

            for (int i = 0; i < actores.tamanio(); i++) {
                try {
                    Actor actor = actores.obtener(i);
                    if (actor == null) continue;
                    String nombre = actor.getNombre();

                    ActorStats stats = tablaActores.obtener(nombre);
                    if (stats == null) {
                        stats = new ActorStats(nombre);
                        tablaActores.agregar(nombre, stats);
                    }

                    stats.agregarEvaluacion();

                    if (!actoresYaContados.contiene(nombre)) {
                        stats.agregarPelicula();
                        actoresYaContados.agregar(nombre);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
