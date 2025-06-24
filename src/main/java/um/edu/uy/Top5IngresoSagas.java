package um.edu.uy;


import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.Saga;
import um.edu.uy.tads.HashTableCerrada;
import um.edu.uy.tads.Heap;

public class Top5IngresoSagas {

    private HashTableCerrada<Saga,Saga> sagaHash;

    public Top5IngresoSagas() {
        this.sagaHash=sagaHash;
    }

    public String getTopSagas(String idioma){
        Saga[] sagaArray = sagaHash.getValuesArray();

        if(sagaArray.length<10){
            System.out.println("No hay suficientes sagas");
            return null;
        }
        Saga[] top10 = new Saga[10];

        Heap<Saga> heapSagas = new Heap<>(sagaArray.length, false);
        for (Saga saga : sagaArray) {
            heapSagas.agregar(saga);
        }
        for (int i = 0; i < 10; i++) {
            top10[i] = heapSagas.obtenerYEliminar();
        }
        String resultado = "TOP 5 SAGAS POR INGRESOS"  + "\n" +
                "------------------------------------"+ "\n";

        for (int posSaga=0;posSaga<top10.length;posSaga++){
            Saga saga= top10[posSaga];
            resultado += (posSaga+ 1) + "- " + saga.toString()+ "\n";
        }
        return resultado;
    }
}
