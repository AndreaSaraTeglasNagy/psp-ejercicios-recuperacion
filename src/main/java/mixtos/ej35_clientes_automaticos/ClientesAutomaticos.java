package mixtos.ej35_clientes_automaticos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientesAutomaticos {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        int puerto = 7100;
        String classpath = System.getProperty("java.class.path");

        Process servidor = new ProcessBuilder("java", "-cp", classpath, "mixtos.ej35_clientes_automaticos.ServidorAuto", String.valueOf(puerto)).start();
        Thread.sleep(800); // pequeña espera para arrancar el servidor

        Process[] clientes = new Process[5];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new ProcessBuilder("java", "-cp", classpath, "mixtos.ej35_clientes_automaticos.ClienteAuto", "Cliente" + (i + 1), String.valueOf(puerto)).start();
            leerSalida("cliente" + (i + 1), clientes[i]);
        }

        for (Process cliente : clientes) {
            cliente.waitFor();
        }

        servidor.destroy();
        System.out.println("Prueba automática terminada");
    }

    // Lee datos de una entrada y los muestra.
    private static void leerSalida(String nombre, Process proceso) {
        new Thread(() -> {
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println(nombre + "> " + linea);
                }
            } catch (IOException e) {
                System.err.println("Error leyendo " + nombre);
            }
        }).start();
    }
}
