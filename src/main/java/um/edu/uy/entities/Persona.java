package um.edu.uy.entities;

public abstract class Persona {
    private double id;
    private String nombre;

    public Persona(double id){
        this.id = id;
    }
    public Persona(double id, String nombre){
        this.id = id;
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }

    public double getId() {
        return id;
    }

}
