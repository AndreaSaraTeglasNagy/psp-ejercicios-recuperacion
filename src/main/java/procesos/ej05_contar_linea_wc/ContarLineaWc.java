package procesos.ej05_contar_linea_wc;

import java.io.*;

public class ContarLineaWc {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("linea> ");
                String linea = teclado.readLine();

                if (linea == null || linea.isBlank()) {
                    break;
                }

                ejecutarWc(linea);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo teclado");
        }
    }

    // Ejecuta el proceso y muestra su resultado.
    private static void ejecutarWc(String linea) {
        ProcessBuilder pb = new ProcessBuilder("wc");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = pb.start();

            try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                escritor.write(linea);
                escritor.newLine();
            }

            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando wc");
        }
    }
}
