package hilos.ej12_carrera_con_join;

import java.util.Random;

public class CarreraConJoin {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Thread liebre = new Thread(new Corredor("Liebre"));
        Thread tortuga = new Thread(new Corredor("Tortuga"));
        Thread perro = new Thread(new Corredor("Perro"));

        liebre.start();
        tortuga.start();
        perro.start();

        liebre.join();
        tortuga.join();
        perro.join();

        System.out.println("Carrera terminada");
    }
}
