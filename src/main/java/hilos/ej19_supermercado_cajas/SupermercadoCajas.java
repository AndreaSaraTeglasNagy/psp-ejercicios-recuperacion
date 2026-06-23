package hilos.ej19_supermercado_cajas;

import java.util.concurrent.atomic.AtomicInteger;

public class SupermercadoCajas {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger total = new AtomicInteger(0);
        Caja[] cajas = {new Caja(1, total), new Caja(2, total), new Caja(3, total)};
        Thread[] clientes = new Thread[12];

        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Thread(new ClienteSuper(i + 1, cajas));
            clientes[i].start();
        }

        for (Thread cliente : clientes) cliente.join();
        System.out.println("Total vendido: " + total.get());
    }
}
