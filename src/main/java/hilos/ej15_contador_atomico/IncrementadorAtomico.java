package hilos.ej15_contador_atomico;

import java.util.concurrent.atomic.AtomicInteger;

class IncrementadorAtomico implements Runnable {
    private final AtomicInteger contador;
    private final int veces;

    // Constructor: guarda los datos necesarios.
    public IncrementadorAtomico(AtomicInteger contador, int veces) {
        this.contador = contador;
        this.veces = veces;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        for (int i = 0; i < veces; i++) {
            contador.incrementAndGet();
        }
    }
}
