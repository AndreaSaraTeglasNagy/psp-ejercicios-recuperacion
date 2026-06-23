package hilos.ej13_contador_sin_sincronizar;

public class ContadorSinSincronizar {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        ContadorCompartido contador = new ContadorCompartido();
        Thread[] hilos = new Thread[10];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new Incrementador(contador, 100000));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Esperado: 1000000");
        System.out.println("Real: " + contador.valor);
    }
}
