package um.edu.uy.entities;

public class Evaluacion {
    private Pelicula pelicula;
    private Usuario user;
    private int clasificacion;
    private String fecha;

    public Evaluacion (Pelicula pelicula,Usuario user, int clasificacion, String fecha){
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

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
