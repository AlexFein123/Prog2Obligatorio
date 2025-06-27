package um.edu.uy.entities;

import java.time.LocalDate;

public class Evaluacion {
    private Pelicula pelicula;
    private Usuario user;
    private float clasificacion;
    private LocalDate fecha;

    public Evaluacion (Pelicula pelicula,Usuario user, float clasificacion, LocalDate fecha){
        this.pelicula = pelicula;
        this.user = new Usuario(user.getId());
        this.clasificacion = clasificacion;
        this.fecha = fecha;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula= pelicula;
    }

    public Usuario getUser(){
        return this.user;
    }

    public void setUser(Usuario user){
        this.user = user;
    }

    public float getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(float clasificacion) {
        this.clasificacion = clasificacion;
    }

    public LocalDate getFecha() {return fecha;  }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }
}
