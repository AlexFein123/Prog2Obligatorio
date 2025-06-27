package um.edu.uy.entities;

public abstract class Persona {
    private int id;
    private String nombre;

    public Persona(int id){
        this.id = id;
    }
    public Persona(int id, String nombre){
        this.id = id;
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

}
