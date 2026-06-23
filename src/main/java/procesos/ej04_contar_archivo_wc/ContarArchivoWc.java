package procesos.ej04_contar_archivo_wc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ContarArchivoWc {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ejercicio: contar líneas, palabras y caracteres con wc.");
        String ruta = pedirTexto(teclado, "Fichero a contar", "recursos/04_contar_archivo_wc/texto.txt");
        contarFichero(new File(ruta));
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner teclado, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Ejecuta wc usando el fichero como entrada estándar.
    private static void contarFichero(File fichero) {
        ProcessBuilder pb = new ProcessBuilder("wc");
        pb.redirectInput(fichero);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = pb.start();
            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando wc");
        }
    }
}
