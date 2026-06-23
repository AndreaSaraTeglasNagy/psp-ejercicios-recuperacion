package hilos.ej16_productor_consumidor_simple;

import java.util.Random;

public class ProductorConsumidorSimple {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        ContenedorSimple contenedor = new ContenedorSimple();
        Thread productor = new Thread(new Productor(contenedor));
        Thread consumidor = new Thread(new Consumidor(contenedor));

        productor.start();
        consumidor.start();

        productor.join();
        consumidor.join();
    }
}
