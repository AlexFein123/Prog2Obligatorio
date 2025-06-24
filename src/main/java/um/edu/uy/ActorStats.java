package um.edu.uy;

public class ActorStats {
    private String nombre;
    private int cantidadPeliculas;
    private int cantidadEvaluaciones;

    public ActorStats(String nombre) {
        this.nombre = nombre;
        this.cantidadPeliculas = 0;
        this.cantidadEvaluaciones = 0;
    }

    public void agregarEvaluacion() {
        cantidadEvaluaciones++;
    }

    public void agregarPelicula() {
        cantidadPeliculas++;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadPeliculas() {
        return cantidadPeliculas;
    }

    public int getCantidadEvaluaciones() {
        return cantidadEvaluaciones;
    }


    public int compareTo(ActorStats otro) {
        return Integer.compare(this.cantidadEvaluaciones, otro.cantidadEvaluaciones);
    }
}
