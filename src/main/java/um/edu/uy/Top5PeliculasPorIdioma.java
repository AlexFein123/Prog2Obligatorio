package um.edu.uy;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.Heap;
import um.edu.uy.entities.Pelicula;

public class Top5PeliculasPorIdioma {
    HashTableCerrada<Integer, Pelicula> listaPeliculas;

    public Top5PeliculasPorIdioma(HashTableCerrada listaPeliculas){
        this.listaPeliculas = listaPeliculas;
    }

    public void Top5PorIdioma(){
        String[] idiomas = {"en", "fr", "it", "es", "pt"};

        for (String idioma : idiomas){
            Heap<Pelicula> heapDePelis = new Heap<>(10000, false);
            Pelicula[] ArrayPeliculas = listaPeliculas.getValuesArray();

            for (Pelicula peli : ArrayPeliculas){
                if (peli != null && idioma.equals(peli.getIdiomaOriginal())){
                    heapDePelis.agregar(peli);
                }
            }

            System.out.println("Top 5 para");
            for (int i = 0; i < 5 && !heapDePelis.isEmpty(); i++){
                Pelicula top = heapDePelis.obtenerYEliminar();
                System.out.println(top.getId() + ", " + top.getTitulo() + ", " + top.cantidadDeEvaluacione() + ", " + idioma);

            }
        }
    }
}
