package sockets_tcp.ej26_tcp_eco_simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorTcpEcoSimple {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor TCP eco simple.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 6000);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor y atiende un único cliente.
    private static void iniciarServidor(int puerto) throws IOException {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Esperando un cliente en puerto " + puerto + "...");
            try (Socket socket = servidor.accept();
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                salida.println("Conectado al servidor eco. Escribe salir para cerrar.");
                String linea;
                while ((linea = entrada.readLine()) != null) {
                    if (linea.equalsIgnoreCase("salir")) {
                        salida.println("adios");
                        break;
                    }
                    salida.println("eco: " + linea);
                }
            }
        }
    }
}
