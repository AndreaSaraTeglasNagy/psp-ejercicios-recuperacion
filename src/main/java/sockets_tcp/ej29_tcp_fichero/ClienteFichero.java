package sockets_tcp.ej29_tcp_fichero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteFichero {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Cliente TCP fichero.");
        String host = pedirTexto(scanner, "Host del servidor", "localhost");
        int puerto = pedirEntero(scanner, "Puerto del servidor", 6003);
        String fichero = pedirTexto(scanner, "Fichero que quieres pedir", "recursos/29_tcp_fichero/datos.txt");

        pedirFichero(host, puerto, fichero);
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner scanner, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = scanner.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner scanner, String mensaje, int defecto) {
        return Integer.parseInt(pedirTexto(scanner, mensaje, String.valueOf(defecto)));
    }

    // Pide un fichero al servidor y muestra su contenido.
    private static void pedirFichero(String host, int puerto, String fichero) throws IOException {
        try (Socket socket = new Socket(host, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println(fichero);
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.equals("@fin@")) break;
                System.out.println(linea);
            }
        }
    }
}
