package um.edu.uy.entities;


import um.edu.uy.tads.ListaEnlazada;

public class Usuario extends Persona implements Comparable<Usuario> {
    private ListaEnlazada<Evaluacion> evaluaciones;

    public Usuario(int id) {
        super(id);
        this.evaluaciones = new ListaEnlazada<>();
    }

    public ListaEnlazada<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(ListaEnlazada<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
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
        return "User: " + getId() + "\n";
    }

}