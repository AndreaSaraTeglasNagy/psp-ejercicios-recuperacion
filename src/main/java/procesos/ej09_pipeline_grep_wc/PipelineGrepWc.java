package procesos.ej09_pipeline_grep_wc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class PipelineGrepWc {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ejercicio: pipeline grep + wc.");
        String fichero = pedirTexto(teclado, "Fichero origen", "recursos/09_pipeline_grep_wc/entrada.txt");
        String texto = pedirTexto(teclado, "Texto a buscar", "ERROR");

        ejecutarPipeline(fichero, texto);
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner teclado, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Ejecuta grep y pasa su salida a wc.
    private static void ejecutarPipeline(String fichero, String texto) {
        ProcessBuilder pbGrep = new ProcessBuilder("grep", texto, fichero);
        ProcessBuilder pbWc = new ProcessBuilder("wc");
        pbGrep.redirectError(ProcessBuilder.Redirect.INHERIT);
        pbWc.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pbWc.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process grep = pbGrep.start();
            Process wc = pbWc.start();

            copiar(grep.getInputStream(), wc.getOutputStream());

            grep.waitFor();
            wc.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando pipeline");
        }
    }

    // Copia datos de una entrada a una salida.
    private static void copiar(InputStream entrada, OutputStream salida) throws IOException {
        try (entrada; salida) {
            byte[] buffer = new byte[1024];
            int leidos;
            while ((leidos = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, leidos);
            }
        }
    }
}
