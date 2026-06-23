package procesos.ej03_filtrar_lineas_grep;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FiltrarLineasGrep {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ejercicio: filtrar líneas de un fichero usando grep.");
        String rutaOrigen = pedirTexto(teclado, "Fichero origen", "recursos/03_filtrar_lineas_grep/entrada.txt");
        String texto = pedirTexto(teclado, "Texto a buscar", "ERROR");
        String rutaDestino = pedirTexto(teclado, "Fichero destino", "recursos/03_filtrar_lineas_grep/salida.txt");

        filtrarLineas(new File(rutaOrigen), texto, new File(rutaDestino));
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner teclado, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Ejecuta grep redirigiendo entrada, salida y error.
    private static void filtrarLineas(File origen, String texto, File destino) {
        ProcessBuilder pb = new ProcessBuilder("grep", texto);
        pb.redirectInput(origen);
        pb.redirectOutput(destino);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = pb.start();
            int codigo = proceso.waitFor();
            System.out.println("grep terminó con código: " + codigo);
            System.out.println("Resultado guardado en: " + destino.getPath());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando grep");
        }
    }
}
