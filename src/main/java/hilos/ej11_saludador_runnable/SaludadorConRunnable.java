package hilos.ej11_saludador_runnable;

public class SaludadorConRunnable {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[6];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new Saludador(i + 1));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }
    }
}
