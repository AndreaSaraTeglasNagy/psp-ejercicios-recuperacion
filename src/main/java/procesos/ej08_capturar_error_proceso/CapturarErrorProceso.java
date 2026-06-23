package procesos.ej08_capturar_error_proceso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CapturarErrorProceso {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", "ClaseQueNoExiste");

        try {
            Process proceso = pb.start();

            Thread salidaNormal = new Thread(() -> leerStream("OUT", proceso.getInputStream()));
            Thread salidaError = new Thread(() -> leerStream("ERR", proceso.getErrorStream()));

            salidaNormal.start();
            salidaError.start();

            int codigo = proceso.waitFor();
            salidaNormal.join();
            salidaError.join();

            System.out.println("Código de salida: " + codigo);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando proceso");
        }
    }

    // Lee datos de una entrada y los muestra.
    private static void leerStream(String nombre, java.io.InputStream stream) {
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(stream))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(nombre + "> " + linea);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo " + nombre);
        }
    }
}
