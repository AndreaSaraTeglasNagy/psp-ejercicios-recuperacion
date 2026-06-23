package hilos.ej15_contador_atomico;

import java.util.concurrent.atomic.AtomicInteger;

public class ContadorAtomico {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger contador = new AtomicInteger(0);
        Thread[] hilos = new Thread[10];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new IncrementadorAtomico(contador, 100000));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Resultado: " + contador.get());
    }
}
