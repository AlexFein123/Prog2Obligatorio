package um.edu.uy.entities;

import um.edu.uy.tads.ListaEnlazada;

public class Actor extends Persona {
    private ListaEnlazada<Pelicula> pelisDondeActua;

    public Actor(double id, String nombre) {
        super(id, nombre);
        this.pelisDondeActua = new ListaEnlazada<>();
    }
    public ListaEnlazada<Pelicula> getPelisDondeActua(){
        return pelisDondeActua;
    }

    public void setPelisDondeActua(ListaEnlazada<Pelicula> pelisDondeActua){
        this.pelisDondeActua = pelisDondeActua;
    }

    public void agregarPelicula(Pelicula peli){
        this.pelisDondeActua.agregar(peli);
    }
}
