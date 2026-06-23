package mixtos.ej32_wc_concurrente_con_hilos;

import java.util.Scanner;

public class WcConcurrenteConHilos {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ejercicio: varios hilos lanzan procesos wc.");
        System.out.println("Escribe rutas separadas por espacios.");
        System.out.println("Pulsa Enter para usar los ficheros de ejemplo.");
        System.out.print("Ficheros: ");
        String linea = teclado.nextLine();

        String[] ficheros;
        if (linea.isBlank()) {
            ficheros = new String[] {
                    "recursos/32_wc_concurrente_con_hilos/a.txt",
                    "recursos/32_wc_concurrente_con_hilos/b.txt",
                    "recursos/32_wc_concurrente_con_hilos/c.txt"
            };
        } else {
            ficheros = linea.trim().split("\\s+");
        }

        lanzarHilos(ficheros);
    }

    // Crea y lanza un hilo por cada fichero.
    private static void lanzarHilos(String[] ficheros) throws InterruptedException {
        Thread[] hilos = new Thread[ficheros.length];
        for (int i = 0; i < ficheros.length; i++) {
            hilos[i] = new Thread(new HiloWc(ficheros[i]));
            hilos[i].start();
        }

        for (Thread hilo : hilos) hilo.join();
        System.out.println("Todos los procesos wc han terminado");
    }
}
