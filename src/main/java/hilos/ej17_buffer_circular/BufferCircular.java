package hilos.ej17_buffer_circular;

import java.util.Random;

public class BufferCircular {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        BufferNumeros buffer = new BufferNumeros(4);
        Thread[] hilos = {
                new Thread(new ProductorBuffer(1, buffer)),
                new Thread(new ProductorBuffer(2, buffer)),
                new Thread(new ConsumidorBuffer(1, buffer)),
                new Thread(new ConsumidorBuffer(2, buffer))
        };

        for (Thread hilo : hilos) hilo.start();
        for (Thread hilo : hilos) hilo.join();
    }
}
