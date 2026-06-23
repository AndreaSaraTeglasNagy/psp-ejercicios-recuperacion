package procesos.ej06_contar_bloque_wc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContarBloqueWc {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("bloque> ");
                String linea = teclado.readLine();

                if (linea == null) {
                    break;
                }

                if (linea.isBlank()) {
                    ejecutarWc(lineas);
                    lineas.clear();
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo teclado");
        }
    }

    // Ejecuta el proceso y muestra su resultado.
    private static void ejecutarWc(List<String> lineas) {
        ProcessBuilder pb = new ProcessBuilder("wc");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = pb.start();

            try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                for (String linea : lineas) {
                    escritor.write(linea);
                    escritor.newLine();
                }
            }

            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando wc");
        }
    }
}
