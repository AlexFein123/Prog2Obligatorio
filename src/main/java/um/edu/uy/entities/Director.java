package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class Director extends Persona implements Comparable<Director> {
    private ListaEnlazada<Pelicula> peliculasdelDirector;
    public Director(double id, String nombre) {
        super(id);
        this.peliculasdelDirector= new ListaEnlazada<>();
    }

    public ListaEnlazada<Pelicula> getPeliculasdelDirector() {
        return peliculasdelDirector;
    }

    public int calificacionMedia()  {
        if (peliculasdelDirector.isEmpty()){
            return 0; //no tiene peliculas
        }
        int suma=0;
        int total=peliculasdelDirector.tamanio();
        for(int enPeliculas=0;enPeliculas<total;enPeliculas++){
            try {
                Pelicula pelicula = peliculasdelDirector.obtenervalorposicion(enPeliculas);
                suma+= pelicula.calificacionMedia();
            } catch (FueraDeRango e) {
                throw new IllegalStateException("no puede ser mayor el rango");
            }

        }
        return (suma/total);
    }

    public int totalEvaluaciones(){
        if (peliculasdelDirector.isEmpty()){
            return 0;
        }
        int totEvaluaciones=0;
        for (int enPelicula=0;enPelicula<peliculasdelDirector.tamanio();enPelicula++){
            try {
                Pelicula pelicula= peliculasdelDirector.obtenervalorposicion(enPelicula);
                totEvaluaciones+= pelicula.getEvaluaciones().tamanio();
            } catch (FueraDeRango e) {
                throw new IllegalStateException("no puede ser mayor el rango");
            }
        }
        return  totEvaluaciones;

    }

    @Override
    public int compareTo(Director director) { //mejor media calificacion
        int valor=-1;
        if(this.calificacionMedia()== director.calificacionMedia()){
            valor=0;
        }else if(this.calificacionMedia()>director.calificacionMedia()){
            valor=1; //mejor clasificacion esta que la otra
        }
        return valor;
    }
    @Override
    public String toString(){
        return "Nombre director: " + getNombre()+ "/n" +
                "Cantidad peliculas: " + getPeliculasdelDirector().tamanio()+"/n" +
                "Calificación media: "+ calificacionMedia()+ "/n";
    }


}