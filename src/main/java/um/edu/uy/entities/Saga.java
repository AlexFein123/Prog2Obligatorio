package um.edu.uy.entities;

import um.edu.uy.tads.ListaEnlazada;

public class Saga { //Nota de github, para subir al repositorio primero se tiene que hacer un commit para aniadir a la rama y luego el push para el github
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
}
