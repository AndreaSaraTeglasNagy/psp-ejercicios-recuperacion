package hilos.ej16_productor_consumidor_simple;

import java.util.Random;

class Consumidor implements Runnable {
    private final ContenedorSimple contenedor;

    // Constructor: guarda los datos necesarios.
    public Consumidor(ContenedorSimple contenedor) {
        this.contenedor = contenedor;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                contenedor.consumir();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
