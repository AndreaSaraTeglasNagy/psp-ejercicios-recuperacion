package hilos.ej16_productor_consumidor_simple;

import java.util.Random;

class Productor implements Runnable {
    private final ContenedorSimple contenedor;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public Productor(ContenedorSimple contenedor) {
        this.contenedor = contenedor;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                contenedor.producir(random.nextInt(100));
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
