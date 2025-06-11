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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }
}
