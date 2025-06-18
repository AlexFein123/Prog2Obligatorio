package um.edu.uy.entities;


import um.edu.uy.tads.ListaEnlazada;

public class Usuario extends Persona implements Comparable<Usuario> {

    private long idUsuario;

    private ListaEnlazada<Evaluacion> evaluaciones;

    public Usuario(double id, String nombre) {
        super(id, nombre);
        this.evaluaciones = new ListaEnlazada<>();
    }


    public long getIdUsuario() {
        return idUsuario;
    }

    public void setEvaluacion(Evaluacion e){
        evaluaciones.agregarOrdenado(e);
    }



    @Override
    public int compareTo(Usuario user) { //mas evaluaciones comparados que user
        int valor=-1;
        if(this.evaluaciones.tamanio()==user.evaluaciones.tamanio()){
            valor=0;
        }else if(this.evaluaciones.tamanio()>user.evaluaciones.tamanio()){
            valor=1;
        }
        return valor;

    }


    @Override
    public String toString() {
        return "User: " + idUsuario + "\n\n";
    }

}