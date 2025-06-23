package um.edu.uy;


import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.Heap;

public class TopPeliculasCalificadas  {
    private HashTableCerrada<Pelicula,Pelicula> peliculaHash;
    public TopPeliculasCalificadas(HashTableCerrada<Pelicula, Pelicula> peliculaHash) {
        this.peliculaHash = peliculaHash;
    }
    public String top10MejoresCalificadasMedia(){
        Pelicula[] peliculasArray = peliculaHash.getValuesArray();

        if(peliculasArray.length<10){
            System.out.println("No hay suficientes peliculas");
            return null;
        }

        Pelicula[] top10 = new Pelicula[10];

        Heap<Pelicula> heapCalificacionesPeliculas = new Heap<>(peliculasArray.length, false);
        for (Pelicula pelicula : peliculasArray) {
            if (pelicula.cantidadDeEvaluaciones()>100){
                heapCalificacionesPeliculas.agregar(pelicula);
            }
        }

        for (int i = 0; i < 10; i++) {
            top10[i] = heapCalificacionesPeliculas.obtenerYEliminar();
        }
        String resultado="TOP 10 PELICULAS POR CALIFICACIÓN:" + "\n" +
                "------------------------------------+" + "\n";
        for (int posPelicula=0;posPelicula<top10.length;posPelicula++){
            Pelicula pelicula= top10[posPelicula];
            resultado += (posPelicula+ 1) + "- " + pelicula.toString()+ "\n";
        }
        return resultado;
    }

}


