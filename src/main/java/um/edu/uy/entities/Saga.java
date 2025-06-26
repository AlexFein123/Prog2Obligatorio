package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

public class Saga implements Comparable<Saga> {
    private int id;
    private String nombre;
    private ListaEnlazada<Pelicula> peliculas;

    public Saga(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.peliculas = new ListaEnlazada<>();
    }

    public ListaEnlazada<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ListaEnlazada<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long sumaIngreso() {
        long ingresoSaga=0;
        for (int enPeliculas = 0; enPeliculas < peliculas.tamanio(); enPeliculas++) {
            try {
                Pelicula pelicula = peliculas.obtenervalorposicion(enPeliculas);
                ingresoSaga+= pelicula.getIngreso();
            } catch (FueraDeRango e) {
                throw new IllegalStateException("no puede ser mayor el rango");
            }
        }
        return ingresoSaga;
    }

    @Override
    public int compareTo(Saga saga) {
        int resultado=-1;
        if (this.sumaIngreso()==saga.sumaIngreso()){
            resultado= 0;
        }
        if (this.sumaIngreso()>saga.sumaIngreso()){
             resultado= 1;
        }
        return resultado;
    }
    @Override
    public String toString() {
        String resultado= "Saga: " + "id= " + id + "\n"+
                "Nombre=" + nombre + "\n"+
                "cantidad de peliculas= " + peliculas.tamanio()+ "\n"
                + "id peliculas= ";

        for (Pelicula pelicula: peliculas){
            resultado+= pelicula.getId() +"\n";
                }
        return resultado+="\n" +
                "ingreso generados= " + sumaIngreso() +"\n";
    }

}

