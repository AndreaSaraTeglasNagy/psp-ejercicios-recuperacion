package hilos.ej19_supermercado_cajas;

import java.util.Random;

class ClienteSuper implements Runnable {
    private final int id;
    private final Caja[] cajas;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public ClienteSuper(int id, Caja[] cajas) {
        this.id = id;
        this.cajas = cajas;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(200, 1000));
            int importe = random.nextInt(5, 100);
            Caja caja = cajas[random.nextInt(cajas.length)];
            caja.pagar(id, importe);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
