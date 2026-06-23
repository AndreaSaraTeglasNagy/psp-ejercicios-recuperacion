package hilos.ej17_buffer_circular;

import java.util.Random;

class ProductorBuffer implements Runnable {
    private final int id;
    private final BufferNumeros buffer;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public ProductorBuffer(int id, BufferNumeros buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                buffer.meter(random.nextInt(100));
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Productor " + id + " termina");
    }
}
