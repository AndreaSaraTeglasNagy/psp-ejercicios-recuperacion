package hilos.ej18_cuentas_bancarias;

public class CuentasBancarias {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Cuenta[] cuentas = {
                new Cuenta(1, 1000),
                new Cuenta(2, 1000),
                new Cuenta(3, 1000)
        };

        Thread[] hilos = new Thread[5];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new HiloTransferencias(cuentas));
            hilos[i].start();
        }

        for (Thread hilo : hilos) hilo.join();

        int total = 0;
        for (Cuenta cuenta : cuentas) {
            System.out.println("Cuenta " + cuenta.getId() + ": " + cuenta.getSaldo());
            total += cuenta.getSaldo();
        }
        System.out.println("Total final: " + total);
    }
}
