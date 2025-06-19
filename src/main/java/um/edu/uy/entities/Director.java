package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class Director extends Persona {
    private ListaEnlazada<Pelicula> peliculasdelDirector;
    public Director(double id, String nombre) {
        super(id, nombre);
        this.peliculasdelDirector= new ListaEnlazada<>();
    }
    public int calificacionMedia() throws FueraDeRango {
        if (peliculasdelDirector.isEmpty()){
            return 0; //no tiene peliculas
        }
        int suma=0;
        int total=0;
        for(int enPeliculas=0;enPeliculas<peliculasdelDirector.tamanio();enPeliculas++){
            Pelicula pelicula = peliculasdelDirector.obtenervalorposicion(enPeliculas);
            suma+= pelicula.calificacionMedia();
            total++;
        }
        return (suma/total);
    }

}
