package um.edu.uy.Entities;

public class Evaluacion {
    private int idpelicula;
    private double idpersona;
    private int clasificacion;
    private String fecha;

    public Evaluacion (int idpelicula, double idpersona, int clasificacion, String fecha){
        this.idpelicula = idpelicula;
        this.idpersona = idpersona;
        this.clasificacion = clasificacion;
        this.fecha = fecha;
    }

    public double getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(double idpersona) {
        this.idpersona = idpersona;
    }

    public int getIdpelicula() {
        return idpelicula;
    }

    public void setIdpelicula(int idpelicula) {
        this.idpelicula = idpelicula;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
