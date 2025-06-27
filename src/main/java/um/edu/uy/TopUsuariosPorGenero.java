package um.edu.uy;

import um.edu.uy.entities.Evaluacion;
import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.HashTableAbierta;
import um.edu.uy.tads.ListaEnlazada;
import um.edu.uy.tads.NodoHash;

public class TopUsuariosPorGenero {
    private ListaEnlazada<Evaluacion> evaluaciones;

    public TopUsuariosPorGenero(ListaEnlazada<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public String topUsuariosPorGenero() {
        HashTableAbierta<String, HashTableAbierta<Double, Double>> generoUsuarios = new HashTableAbierta<>(100);
        HashTableAbierta<String, Integer> conteoGenero = new HashTableAbierta<>(100);

        for (int i = 0; i < evaluaciones.tamanio(); i++) {
            Evaluacion e;
            try {
                e = evaluaciones.obtenervalorposicion(i);
            } catch (FueraDeRango ex) {
                continue;
            }

            double idUsuario = e.getUser().getId();
            ListaEnlazada<String> generos = e.getPelicula().getGenero();

            for (int j = 0; j < generos.tamanio(); j++) {
                String genero;
                try {
                    genero = generos.obtenervalorposicion(j);
                } catch (FueraDeRango ex) {
                    continue;
                }

                // Actualizar conteo total por género
                Integer total = conteoGenero.obtener(genero);
                conteoGenero.agregar(genero, total == null ? 1 : total + 1);

                // Actualizar conteo por usuario
                HashTableAbierta<Double, Double> usuarios = generoUsuarios.obtener(genero);
                if (usuarios == null) {
                    usuarios = new HashTableAbierta<>(50);
                    generoUsuarios.agregar(genero, usuarios);
                }

                Double cuenta = usuarios.obtener(idUsuario);
                usuarios.agregar(idUsuario, cuenta == null ? 1.0 : cuenta + 1.0);
            }
        }

        // Obtener top 10 géneros
        String[] topGeneros = obtenerTop10Generos(conteoGenero);

        StringBuilder resultado = new StringBuilder("TOP USUARIOS POR GÉNERO:\n--------------------------\n");
        for (String genero : topGeneros) {
            if (genero == null) continue;

            HashTableAbierta<Double, Double> usuarios = generoUsuarios.obtener(genero);
            double topUser = -1;
            double max = -1;

            for (int i = 0; i < usuarios.getCapacidad(); i++) {
                ListaEnlazada t = usuarios.getTabla()[i];
                for (int j = 0; j < t.tamanio(); j++) {
                    try {
                        var nodo = (NodoHash<Double, Double>) t.obtenervalorposicion(j);
                        if (nodo.getValor() > max) {
                            max = nodo.getValor();
                            topUser = nodo.getClave();
                        }
                    } catch (FueraDeRango ex) {
                        continue;
                    }
                }
            }

            resultado.append((int) topUser).append(",").append(genero).append(",").append((int) max).append("\n");
        }

        return resultado.toString();
    }

    private String[] obtenerTop10Generos(HashTableAbierta<String, Integer> generoConteo) {
        ListaEnlazada<NodoHash<String, Integer>>[] tabla = generoConteo.getTabla();
        String[] top10 = new String[10];
        int[] valores = new int[10];

        for (int i = 0; i < generoConteo.getCapacidad(); i++) {
            ListaEnlazada<NodoHash<String, Integer>> bucket = tabla[i];
            for (int j = 0; j < bucket.tamanio(); j++) {
                NodoHash<String, Integer> nodo;
                try {
                    nodo = bucket.obtenervalorposicion(j);
                } catch (FueraDeRango ex) {
                    continue;
                }

                String gen = nodo.getClave();
                int val = nodo.getValor();

                for (int k = 0; k < 10; k++) {
                    if (top10[k] == null || val > valores[k]) {
                        for (int m = 9; m > k; m--) {
                            top10[m] = top10[m - 1];
                            valores[m] = valores[m - 1];
                        }
                        top10[k] = gen;
                        valores[k] = val;
                        break;
                    }
                }
            }
        }

        return top10;
    }
}