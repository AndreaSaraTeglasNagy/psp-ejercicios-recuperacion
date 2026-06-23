package hilos.ej14_contador_sincronizado;

public class ContadorSincronizado {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        ContadorSeguro contador = new ContadorSeguro();
        Thread[] hilos = new Thread[10];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new HiloIncrementador(contador, 100000));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Resultado: " + contador.getValor());
    }
}
