package um.edu.uy.entities;

import um.edu.uy.tads.ListaEnlazada;

public class Director extends Persona {
    private ListaEnlazada<Pelicula> peliculasDelDirector;
    public Director(double id, String nombre) {
        super(id, nombre);
        this.peliculasDelDirector= new ListaEnlazada<>();
    }

    public int mediaCalificaciones(){
        if (peliculasDelDirector.isEmpty()){
            return 0; //director sin peliculas
        }
        int suma=0;
        int total=0;

        for(int enPelicula=0;enPelicula<peliculasDelDirector.getSize();enPelicula++){
            Pelicula pelicula= peliculasDelDirector.get(enPelicula);
            suma += pelicula.mediaCalificaciones();
            total++;
        }
        return (suma/total);
    }

}
