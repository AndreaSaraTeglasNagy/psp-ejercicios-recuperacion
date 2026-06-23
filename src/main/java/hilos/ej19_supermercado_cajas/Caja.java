package hilos.ej19_supermercado_cajas;

import java.util.concurrent.atomic.AtomicInteger;

class Caja {
    private final int id;
    private final AtomicInteger totalSupermercado;

    // Constructor: guarda los datos necesarios.
    public Caja(int id, AtomicInteger totalSupermercado) {
        this.id = id;
        this.totalSupermercado = totalSupermercado;
    }

    // Simula el pago de un cliente en una caja.
    public synchronized void pagar(int idCliente, int importe) throws InterruptedException {
        System.out.printf("Caja %d atiende al cliente %d por %d euros%n", id, idCliente, importe);
        Thread.sleep(300);
        totalSupermercado.addAndGet(importe);
        System.out.printf("Cliente %d sale de caja %d%n", idCliente, id);
    }
}
