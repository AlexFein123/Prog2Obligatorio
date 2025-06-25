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
    private ListaEnlazada<String> idiomasHablados;  // NUEVO

    public Pelicula(int id, String titulo, ListaEnlazada<String> genero, String idiomaOriginal,
                    long ingreso, LocalDate fecha, Director director, ListaEnlazada<String> idiomasHablados) {
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
    public ListaEnlazada<Actor> getActores() {
        return actores;
    }

    public Director getDirector() {
        return director;
    }
    public void debugPrintDirector() {
        if (director == null) {
            System.out.println("Director es NULL en la película ID " + id);
        } else {
            System.out.println("Director asignado en película ID " + id + ": " + director.getNombre());
        }
    }

    public void setActores(ListaEnlazada<Actor> actores) {
        this.actores = actores;
    }

    public int calificacionMedia() {
        if (evaluaciones.isEmpty()) {
            return 0; // no tiene evaluaciones
        }
        int suma = 0;
        int total = 0;
        for (int enEvaluaciones = 0; enEvaluaciones < evaluaciones.tamanio(); enEvaluaciones++) {
            Evaluacion evaluacion = null;
            try {
                evaluacion = evaluaciones.obtenervalorposicion(enEvaluaciones);
            } catch (FueraDeRango e) {
                throw new IllegalStateException("no posible");
            }
            suma += evaluacion.getClasificacion();
            total++;
        }
        return (suma / total);
    }

    public ListaEnlazada<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public ListaEnlazada<String> getIdiomasHablados() {  // NUEVO getter
        return idiomasHablados;
    }

    public void setIdiomasHablados(ListaEnlazada<String> idiomasHablados) {  // NUEVO setter
        this.idiomasHablados = idiomasHablados;
    }

    @Override
    public int compareTo(Pelicula pelicula) { // mejor calificación
        int valor = -1;
        if (this.calificacionMedia() == pelicula.calificacionMedia()) {
            valor = 0;
        } else if (this.calificacionMedia() > pelicula.calificacionMedia()) {
            valor = 1; // mejor calificación esta que la otra
        }
        return valor;
    }

    @Override
    public String toString() {
        return "Id de la película: " + getId() + "\n" +
                "Título de la película: " + getTitulo() + "\n" +
                "Calificación media: " + calificacionMedia() + "\n" +
                "Idiomas hablados: " + idiomasHablados.toString() + "\n";
    }
}

