package mixtos.ej35_clientes_automaticos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAuto {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        String nombre;
        int puerto;

        if (args.length >= 2) {
            // Estos argumentos los usa ClientesAutomaticos al lanzar procesos hijo.
            nombre = args[0];
            puerto = Integer.parseInt(args[1]);
        } else {
            Scanner teclado = new Scanner(System.in);
            nombre = pedirTexto(teclado, "Nombre del cliente", "ClienteManual");
            puerto = pedirEntero(teclado, "Puerto del servidor", 7100);
        }

        conectar(nombre, puerto);
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner teclado, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        return Integer.parseInt(pedirTexto(teclado, mensaje, String.valueOf(defecto)));
    }

    // Conecta con el servidor automático y muestra la respuesta.
    private static void conectar(String nombre, int puerto) throws IOException {
        try (Socket socket = new Socket("localhost", puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("hola soy " + nombre);
            System.out.println(nombre + " recibe: " + entrada.readLine());
        }
    }
}
