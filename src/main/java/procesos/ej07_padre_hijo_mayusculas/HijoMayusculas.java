package procesos.ej07_padre_hijo_mayusculas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HijoMayusculas {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println(linea.toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Hijo: error leyendo entrada");
        }
    }
}
