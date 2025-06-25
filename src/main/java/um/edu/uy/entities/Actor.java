package um.edu.uy.entities;

import um.edu.uy.exceptions.FueraDeRango;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.ListaEnlazada;

import java.time.LocalDate;

public class Actor extends Persona   {
    private ListaEnlazada<Pelicula> pelisDondeActua;

    public Actor(double id, String nombre) {
        super(id, nombre);
        this.pelisDondeActua = new ListaEnlazada<>();
    }

    public ListaEnlazada<Pelicula> getPelisDondeActua() {
        return pelisDondeActua;
    }

    public void agregarPelicula(Pelicula peli) {
        this.pelisDondeActua.agregar(peli);
    }

    public int[] calificacionPorMes() {
        int[] calificacionPorMes = new int[12];
        for (int indiceDondeActua = 0; indiceDondeActua < pelisDondeActua.tamanio(); indiceDondeActua++) {
            try {
                Pelicula pelicula = pelisDondeActua.obtenervalorposicion(indiceDondeActua);
                for (int indiceEvaluacionesPelicula = 0; indiceEvaluacionesPelicula < pelicula.cantidadDeEvaluaciones(); indiceEvaluacionesPelicula++) {
                    Evaluacion evaluacion = pelicula.getEvaluaciones().obtenervalorposicion(indiceEvaluacionesPelicula);
                    int mesCalificado = evaluacion.getFecha().getMonthValue();
                    calificacionPorMes[mesCalificado - 1]++; //-1 porque array va de 0 a 11
                }
            } catch (FueraDeRango e) {
                throw new IllegalStateException();
            }

        }
        return calificacionPorMes;
    }
    public int getcalificacionPorMes(int mes) {
        int[]  anio = calificacionPorMes();
        return  anio[mes-1];
    }

    public int[] peliculasDistintasPorMes() {
        int[] peliculasPorMes = new int[12];
        HashTableCerrada<Integer, Pelicula>[] peliculasYaContadasPorMes = new HashTableCerrada[12];
        //creo 12 hash cerrados

        for (int meses = 0; meses < 12; meses++) {
            peliculasYaContadasPorMes[meses] = new HashTableCerrada<>(pelisDondeActua.tamanio());
        }

        for (int indiceDondeActua = 0; indiceDondeActua < pelisDondeActua.tamanio(); indiceDondeActua++) {
            try {
                Pelicula pelicula = pelisDondeActua.obtenervalorposicion(indiceDondeActua);
                for (int indiceEvaluacionesPelicula = 0; indiceEvaluacionesPelicula < pelicula.cantidadDeEvaluaciones(); indiceEvaluacionesPelicula++) {
                    Evaluacion evaluacion = pelicula.getEvaluaciones().obtenervalorposicion(indiceEvaluacionesPelicula);
                    int mes = evaluacion.getFecha().getMonthValue() - 1;
                    if (peliculasYaContadasPorMes[mes].obtener(pelicula.getId()) == null) {//pelicula no fue contada
                        peliculasYaContadasPorMes[mes].agregar(pelicula.getId(), pelicula);
                        peliculasPorMes[mes]++;
                    }

                }

            } catch (FueraDeRango e) {
                throw new IllegalStateException("No cumple con el rango ", e);
            }
        }
        return peliculasPorMes;

    }
   public int getPeliculasDistintasPorMes(int mes) {
        int[]  anio = peliculasDistintasPorMes();
        return  anio[mes-1];
   }


}