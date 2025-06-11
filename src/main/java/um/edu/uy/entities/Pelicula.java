package um.edu.uy.entities;

public class Pelicula {
    private int id;
    private String titulo;
    private String idioma;
    private int ingreso;

    public Pelicula(int id, String titulo, String idioma, int ingreso){
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
        this.ingreso = ingreso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }
}
