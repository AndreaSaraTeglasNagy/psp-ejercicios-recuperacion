package hilos.ej17_buffer_circular;

import java.util.Random;

class ConsumidorBuffer implements Runnable {
    private final int id;
    private final BufferNumeros buffer;

    // Constructor: guarda los datos necesarios.
    public ConsumidorBuffer(int id, BufferNumeros buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                buffer.sacar();
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Consumidor " + id + " termina");
    }
}
