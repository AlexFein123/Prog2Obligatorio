package um.edu.uy.Entities;


import um.edu.uy.List.ListaEnlazada;

public class Usuario implements Comparable<Usuario> {

    private long idUsuario;
    private String nombreUsuario;
    private ListaEnlazada<Usuario> evaluaciones;


    public Usuario(long idUsuario, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.evaluaciones = new ListaEnlazada<>();
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void agregarEvaluacion(Pelicula pelicula, float calificacion) {
        evaluaciones.agregar(pelicula);

    }


    @Override
    public int compareTo(Usuario user) { //mas libros comparados que user
        int valor=-1;
        if(this.evaluaciones.getSize()==user.evaluaciones.getSize()){
            valor=0;
        }else if(this.evaluaciones.getSize()>user.evaluaciones.getSize()){
            valor=1;
        }
        return valor;

    }



    @Override
    public String toString() {
        return "User: " + idUsuario + "\n\n";
    }

}