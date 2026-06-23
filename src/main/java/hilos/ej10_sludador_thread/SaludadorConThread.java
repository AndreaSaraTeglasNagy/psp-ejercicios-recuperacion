package hilos.ej10_sludador_thread;

public class SaludadorConThread {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[6];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloSaludador(i + 1);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Terminan todos los hilos");
    }
}
