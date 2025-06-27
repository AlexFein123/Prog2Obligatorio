package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class Director extends Persona implements Comparable<Director> {
    private ListaEnlazada<Pelicula> peliculasdelDirector;

    public Director(int id, String nombre) {
        super(id, nombre);
        this.peliculasdelDirector = new ListaEnlazada<>();
    }

    public ListaEnlazada<Pelicula> getPeliculasdelDirector() {
        return peliculasdelDirector;
    }

    public double calificacionMedia()  {
        if (peliculasdelDirector.isEmpty()){
            return 0; //no tiene peliculas
        }
        double suma=0;
        double total=peliculasdelDirector.tamanio();
        for(int enPeliculas=0;enPeliculas<total;enPeliculas++){
            try {
                Pelicula pelicula = peliculasdelDirector.obtenervalorposicion(enPeliculas);
                suma+= pelicula.calificacionMedia();
            } catch (FueraDeRango e) {
                throw new IllegalStateException("no puede ser mayor el rango");
            }

        }
        return  (suma / total);
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
           return Double.compare(this.calificacionMedia(), director.calificacionMedia());
    }
    @Override
    public String toString(){
        return "Nombre director: " + getNombre()+ "\n" +
                "Cantidad peliculas: " + getPeliculasdelDirector().tamanio()+"\n" +
                "Calificacion media: "+ calificacionMedia()+ "\n" +
                "Cantidad evaluaciones: "+ totalEvaluaciones() + "\n";
    }


}