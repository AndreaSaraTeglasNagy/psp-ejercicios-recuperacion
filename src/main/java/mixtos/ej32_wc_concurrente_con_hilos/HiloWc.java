package mixtos.ej32_wc_concurrente_con_hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class HiloWc implements Runnable {
    private final String fichero;

    // Constructor: guarda los datos necesarios.
    public HiloWc(String fichero) {
        this.fichero = fichero;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        ProcessBuilder pb = new ProcessBuilder("wc", fichero);
        pb.redirectErrorStream(true);

        try {
            Process proceso = pb.start();
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println("[" + fichero + "] " + linea);
                }
            }
            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error contando " + fichero);
        }
    }
}
