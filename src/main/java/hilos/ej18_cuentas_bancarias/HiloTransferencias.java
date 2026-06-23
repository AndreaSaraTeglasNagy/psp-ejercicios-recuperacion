package hilos.ej18_cuentas_bancarias;

import java.util.Random;

class HiloTransferencias implements Runnable {
    private final Cuenta[] cuentas;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public HiloTransferencias(Cuenta[] cuentas) {
        this.cuentas = cuentas;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            Cuenta origen = cuentas[random.nextInt(cuentas.length)];
            Cuenta destino = cuentas[random.nextInt(cuentas.length)];

            if (origen != destino) {
                origen.transferirA(destino, random.nextInt(1, 50));
            }
        }
    }
}
