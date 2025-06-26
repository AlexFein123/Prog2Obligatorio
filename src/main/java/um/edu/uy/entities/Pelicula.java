package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.ListaEnlazada;

import java.time.LocalDate;

public class Pelicula implements Comparable<Pelicula> {
    private int id;
    private String titulo;
    private ListaEnlazada<String> genero;
    private String idiomaOriginal;
    private long ingreso;
    private LocalDate fecha;
    private ListaEnlazada<Actor> actores;
    private ListaEnlazada<Evaluacion> evaluaciones;
    private Director director;
    private ListaEnlazada<String> idiomasHablados;
    private Saga saga;  // NUEVO

    public Pelicula(int id, String titulo, ListaEnlazada<String> genero, String idiomaOriginal,
                    long ingreso, LocalDate fecha, Director director, ListaEnlazada<String> idiomasHablados, Saga saga) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.idiomaOriginal = idiomaOriginal;
        this.ingreso = ingreso;
        this.fecha = fecha;
        this.actores = new ListaEnlazada<>();
        this.evaluaciones = new ListaEnlazada<>();
        this.director = director;
        this.idiomasHablados = idiomasHablados;
        this.saga = saga;
    }

    public int cantidadDeEvaluaciones() {
        return evaluaciones.tamanio();
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

    public ListaEnlazada<String> getGenero() {
        return genero;
    }

    public void setGenero(ListaEnlazada<String> genero) {
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

    public void setDirector(Director director) {
        this.director = director;
        if (director != null) {
            director.getPeliculasdelDirector().agregarAlFinal(this);
        }
    }

    public Director getDirector() {
        return director;
    }

    public ListaEnlazada<Actor> getActores() {
        return actores;
    }

    public void setActores(ListaEnlazada<Actor> actores) {
        this.actores = actores;
    }

    public ListaEnlazada<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public double calificacionMedia() {
        if (evaluaciones.isEmpty()) return 0;

        int suma = 0;
        for (int i = 0; i < evaluaciones.tamanio(); i++) {
            try {
                suma += evaluaciones.obtenervalorposicion(i).getClasificacion();
            } catch (FueraDeRango e) {
                throw new IllegalStateException("Error accediendo a evaluación");
            }
        }
        return (double) suma / evaluaciones.tamanio();
    }

    public ListaEnlazada<String> getIdiomasHablados() {
        return idiomasHablados;
    }

    public void setIdiomasHablados(ListaEnlazada<String> idiomasHablados) {
        this.idiomasHablados = idiomasHablados;
    }

    public Saga getSaga() {
        return saga;
    }

    public void setSaga(Saga saga) {
        this.saga = saga;
    }

    @Override
    public int compareTo(Pelicula otra) {
      return Double.compare(this.calificacionMedia(), otra.calificacionMedia());
   }


           @Override
    public String toString() {
        return "Id de la pelicula: " + id + "\n" +
                "Titulo: " + titulo + "\n" +
                "Calificacion media: " + calificacionMedia() + "\n" ;
                //"Idiomas hablados: " + idiomasHablados + "\n" +
                //"Saga: " + (saga != null ? saga.getNombre() : "Ninguna") + "\n";
    }
}
