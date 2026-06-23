package procesos.ej01_proceso_java_version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcesoJavaVersion {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        pb.redirectErrorStream(true); // junta salida y error

        try {
            Process proceso = pb.start();

            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int codigo = proceso.waitFor();
            System.out.println("Código de salida: " + codigo);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el proceso");
        }
    }
}
