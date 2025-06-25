package um.edu.uy;

import um.edu.uy.entities.*;

import um.edu.uy.tads.HashTableCerrada;

public class ActorMasCalificadoPorMes {

    private HashTableCerrada<Integer,Actor> actorHash;

    public ActorMasCalificadoPorMes() {this.actorHash = actorHash;}

    public String mejorCalificacionPorMes() {
        Actor[] actorArray = actorHash.getValuesArray();

        Actor[] mejores = new Actor[12];
        int[] maxCalificacionesPorMes = new int[12];
        for (int meses = 0; meses < 12; meses++) {
            maxCalificacionesPorMes[meses] = -1;
        }

        for (Actor actor : actorArray) {
            if (actor!=null){
                int[] calificacionesActorMes = actor.calificacionPorMes(); //array con todas las calificaciones del actor x mes
                for(int mes = 0; mes < 12; mes++) {
                    if (calificacionesActorMes[mes]>maxCalificacionesPorMes[mes]) {
                        maxCalificacionesPorMes[mes] = calificacionesActorMes[mes];
                        mejores[mes] = actor;

                    }
                }
            }
        }
        String resultado ="ACTORES PRINCIPALES POR MES" + "\n" +
                "---------------------------------------" + "\n" ;

        for (int meses = 0; meses < 12; meses++) {
            resultado +="Mes: " + (meses+1) + "\n"
                    +"Actor: "+mejores[meses].getNombre() + "\n"
                    +"Cantidad de Peliculas (distintas): " + mejores[meses].getPeliculasDistintasPorMes(meses) + "\n"
                    +"Cantidad de Calificaciones: " + maxCalificacionesPorMes[meses] + "\n";
        }
        return resultado;
        }
}


