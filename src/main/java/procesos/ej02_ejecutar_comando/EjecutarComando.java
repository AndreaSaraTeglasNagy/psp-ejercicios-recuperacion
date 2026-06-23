package procesos.ej02_ejecutar_comando;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EjecutarComando {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Ejercicio: ejecutar un comando con ProcessBuilder.");
        System.out.print("Escribe el comando completo (ejemplo: java -version): ");
        String linea = teclado.nextLine();

        if (linea.isBlank()) {
            System.out.println("No has escrito ningún comando.");
            return;
        }

        String[] comando = linea.trim().split("\\s+");
        ejecutarComando(comando);
    }

    // Ejecuta el comando indicado y hereda la consola del padre.
    private static void ejecutarComando(String[] comando) {
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.inheritIO();

        try {
            System.out.println("Ejecutando: " + Arrays.toString(comando));
            Process proceso = pb.start();
            int codigo = proceso.waitFor();
            System.out.println("Proceso terminado con código: " + codigo);
        } catch (IOException | InterruptedException e) {
            System.err.println("No se pudo ejecutar el comando");
        }
    }
}
