
package um.edu.uy;

import um.edu.uy.entities.Director;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.Heap;
import um.edu.uy.tads.ListaEnlazada;

public class TopDirectores {
    private HashTableCerrada<Director, Director> directorHash;

    public TopDirectores(HashTableCerrada<Director, Director> directorHash) {
        this.directorHash = directorHash ;
    }


    public String top10MejorCalificacionMedia() {
        Director[] directorArray = directorHash.getValuesArray();

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

        for (int i = 0; i < 10; i++) {
            top10[i] = heapCalificacionesDirector.obtenerYEliminar();
        }
        String resultado="TOP 10 DIRECTORES POR CALIFICACIÓN:" +"\n" +
                "------------------------------------+" +"\n";
        for (int directorPos=0;directorPos<top10.length;directorPos++){
            Director director= top10[directorPos];
            resultado+= (directorPos+ 1) + "- " + director.toString()+ "\n";
        }
        return resultado;
    }

}