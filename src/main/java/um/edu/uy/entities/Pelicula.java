package um.edu.uy.entities;

import um.edu.uy.tads.ListaEnlazada;

import java.time.LocalDate;

public class Pelicula {
    private int id;
    private String titulo;
    private String genero;
    private String idiomaOriginal;
    private long ingreso;
    private LocalDate fecha;
    private ListaEnlazada<Actor> actores;
    private Director director;

    public Pelicula(int id, String titulo, String genero, String idiomaOriginal, long ingreso, LocalDate fecha, Director director){
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.idiomaOriginal = idiomaOriginal;
        this.ingreso = ingreso;
        this.fecha = fecha;
        this.actores = new ListaEnlazada<>();
        this.director = director;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public long getIngreso() {
        return ingreso;
    }

    public void setIngreso(long ingreso) {
        this.ingreso = ingreso;
    }

    public void setDirector(Director director){this.director= director;}

    public Director getDirector (){
        return director;
    }

    public void setActores(ListaEnlazada<Actor> actores) {
        this.actores = actores;
    }



}
