package um.edu.uy.entities;

public class PeliculaPorEvaluaciones implements Comparable<PeliculaPorEvaluaciones>{

    private Pelicula pelicula;

    public PeliculaPorEvaluaciones(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    @Override
    public int compareTo(PeliculaPorEvaluaciones peliculaEvaluaciones) {
        return Integer.compare(this.pelicula.cantidadDeEvaluaciones(), peliculaEvaluaciones.pelicula.cantidadDeEvaluaciones());
    }
}
