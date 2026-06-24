package sockets_tcp.ej37_tcp_buffer_eliminar_valor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteBufferEliminarValor {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.print("IP servidor [127.0.0.1]: ");
        String ip = teclado.nextLine();
        if (ip.isBlank()) {
            ip = "127.0.0.1";
        }

        System.out.print("Puerto [6100]: ");
        String textoPuerto = teclado.nextLine();
        int puerto = textoPuerto.isBlank() ? 6100 : Integer.parseInt(textoPuerto);

        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(entrada.readLine());
            System.out.println("Ejemplo: METER 100 | METER 400 | ELIMINAR 400 | SUMA | VER | SALIR");

            String linea;
            do {
                System.out.print("> ");
                linea = teclado.nextLine();
                salida.println(linea);
                System.out.println(entrada.readLine());
            } while (!linea.equalsIgnoreCase("SALIR"));
        }
    }
}
