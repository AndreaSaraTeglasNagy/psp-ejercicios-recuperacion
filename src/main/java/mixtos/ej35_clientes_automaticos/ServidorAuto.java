package mixtos.ej35_clientes_automaticos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorAuto {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        int puerto;

        if (args.length >= 1) {
            // Este argumento lo usa ClientesAutomaticos al lanzar el proceso servidor.
            puerto = Integer.parseInt(args[0]);
        } else {
            Scanner teclado = new Scanner(System.in);
            puerto = pedirEntero(teclado, "Puerto para escuchar", 7100);
        }

        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor automático.
    private static void iniciarServidor(int puerto) throws IOException {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor automático iniciado en puerto " + puerto);
            while (true) {
                Socket socket = servidor.accept();
                new Thread(() -> atender(socket)).start();
            }
        }
    }

    // Atiende la petición recibida por el cliente.
    private static void atender(Socket socket) {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String linea;
            while ((linea = entrada.readLine()) != null) {
                salida.println("eco " + linea);
            }
        } catch (IOException e) {
            System.err.println("Cliente cerrado");
        }
    }
}
