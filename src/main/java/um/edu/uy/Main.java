package um.edu.uy;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userImput = new Scanner(System.in);  //Defino mi scanner
        boolean salir = false;                       //inicio salir en false para que arranque el bucle

        while (salir == false) {
            System.out.println("Menú principal");
            System.out.println("Seleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            String opcionSeleccionada = userImput.nextLine();


            if (!opcionSeleccionada.equals("1") && !opcionSeleccionada.equals("2") && !opcionSeleccionada.equals("3")) {
                break;
            }
            if (opcionSeleccionada == "1"){
                //aca va la carga de datos;
                break;
            }
            if(opcionSeleccionada == "2"){
                mostrarMenuConsultas(userImput);
                break;
            }
            if(opcionSeleccionada == "3"){
                System.out.println("Saliendo del programa...");
                salir = true;
                break;
            }

        }

    }

    public static void mostrarMenuConsultas(Scanner userImput){
        boolean volver = false;

        while(volver == false ){
            System.out.println("1. Top 5 de las películas que más calificaciones por idioma.");
            System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
            System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
            System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
            System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
            System.out.println("6. Usuarios con más calificaciones por género");
            System.out.println("7. Salir");

            String opcionElegida = userImput.nextLine();

            if(!opcionElegida.equals("1") && !opcionElegida.equals("2") && !opcionElegida.equals("3") && !opcionElegida.equals("4") && !opcionElegida.equals("5") && !opcionElegida.equals("6") && !opcionElegida.equals("7")){
                System.out.println("Ingrese una opcion correcta...");
            }
            if(opcionElegida == "7"){
                System.out.println("Volviendo al menu principal...");
                volver = true;
                break;
            }
        }



    }
}