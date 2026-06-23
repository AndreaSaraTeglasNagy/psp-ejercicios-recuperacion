package procesos.ej07_padre_hijo_mayusculas;

import java.io.*;

public class PadreMayusculas {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "procesos.ej07_padre_hijo_mayusculas.HijoMayusculas");

        try {
            Process hijo = pb.start();

            Thread lectorHijo = new Thread(() -> leerSalidaHijo(hijo));
            lectorHijo.start();

            try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
                 BufferedWriter escritorHijo = new BufferedWriter(new OutputStreamWriter(hijo.getOutputStream()))) {

                while (true) {
                    System.out.print("texto> ");
                    String linea = teclado.readLine();

                    if (linea == null || linea.isBlank()) {
                        break;
                    }

                    escritorHijo.write(linea);
                    escritorHijo.newLine();
                    escritorHijo.flush();
                }
            }

            hijo.waitFor();
            lectorHijo.join();
        } catch (IOException | InterruptedException e) {
            System.err.println("Padre: error trabajando con el hijo");
        }
    }

    // Lee datos de una entrada y los muestra.
    private static void leerSalidaHijo(Process hijo) {
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println("hijo> " + linea);
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer la salida del hijo");
        }
    }
}
