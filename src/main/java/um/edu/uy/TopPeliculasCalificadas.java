package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashOrd;
import um.edu.uy.tads.Heap;
import um.edu.uy.tads.ListaEnlazada;

public class TopPeliculasCalificadas  {


    private HashOrd<Pelicula,Pelicula> peliculaHash;

    public TopPeliculasCalificadas(HashOrd<Pelicula, Pelicula> peliculaHash) {
        this.peliculaHash = peliculaHash;
    }



    public Pelicula[] top10MejorCalificacionMedia() {
        Pelicula[] peliculasArray = peliculaHash.toArray();

        if(peliculasArray.length<10){
            System.out.println("No hay suficientes peliculas");
            return null;
        }

        Pelicula[] top10 = new Pelicula[10];

        Heap<Pelicula> heapCalificacionesPeliculas = new Heap<>(peliculasArray.length, false);
        for (Pelicula pelicula : peliculasArray) {

            heapCalificacionesPeliculas.agregar(pelicula);
        }

        for (int i = 0; i < 10; i++) {
            top10[i] = heapCalificacionesPeliculas.obtenerYEliminar();
        }
        return top10;
    }



    }


