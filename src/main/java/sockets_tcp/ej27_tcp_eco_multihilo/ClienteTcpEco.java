package sockets_tcp.ej27_tcp_eco_multihilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTcpEco {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cliente TCP eco multihilo.");
        String host = pedirTexto(teclado, "Host del servidor", "localhost");
        int puerto = pedirEntero(teclado, "Puerto del servidor", 6001);

        iniciarCliente(host, puerto, teclado);
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

    // Conecta con el servidor y permite escribir órdenes.
    private static void iniciarCliente(String host, int puerto, Scanner teclado) throws IOException {
        try (Socket socket = new Socket(host, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(entrada.readLine());
            while (true) {
                System.out.print("> ");
                String linea = teclado.nextLine();

                salida.println(linea);
                System.out.println(entrada.readLine());

                if (linea.equalsIgnoreCase("salir")) break;
            }
        }
    }
}
