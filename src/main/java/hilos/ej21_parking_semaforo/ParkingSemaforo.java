package hilos.ej21_parking_semaforo;

import java.util.concurrent.Semaphore;

public class ParkingSemaforo {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        Semaphore plazas = new Semaphore(3);

        for (int i = 1; i <= 10; i++) {
            new Thread(new Coche(i, plazas)).start();
        }
    }
}
