package um.edu.uy.Entities;

public abstract class Persona {
    private double id;
    private String nombre;

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
