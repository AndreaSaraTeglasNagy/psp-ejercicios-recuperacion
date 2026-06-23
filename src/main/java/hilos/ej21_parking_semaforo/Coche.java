package hilos.ej21_parking_semaforo;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Coche implements Runnable {
    private final int id;
    private final Semaphore plazas;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public Coche(int id, Semaphore plazas) {
        this.id = id;
        this.plazas = plazas;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            System.out.println("Coche " + id + " espera plaza");
            plazas.acquire();
            System.out.println("Coche " + id + " entra al parking");
            Thread.sleep(random.nextInt(500, 1500));
            System.out.println("Coche " + id + " sale del parking");
            plazas.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
